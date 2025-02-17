package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.entity.MedicationReminder;
import com.beehyv.tbalert.tbalertbackend.service.MedicationReminderService;
import com.beehyv.tbalert.tbalertbackend.service.SMSSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static java.lang.Long.max;

@Service
@RequiredArgsConstructor
@Slf4j
public class SMSSchedulerServiceImpl implements SMSSchedulerService {
    @Value("${app.retry.max-attempts:3}")
    private int maxRetryAttempts;

    @Value("${app.retry.delay-seconds:300}")
    private int retryDelaySeconds;

    @Value("${app.notification.lead-time-seconds:1800}") // 30 minutes default
    private long NOTIFICATION_LEAD_SECONDS;

    @Value("${app.threadPoolSize:10}")
    private int threadPoolSize;

    private PlivoSmsService smsService;

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(threadPoolSize);
    private final Map<Long, ScheduledFuture<?>> scheduledReminders = new ConcurrentHashMap<>();
    private final MedicationReminderService medicationReminderService;

    @Override
    public void scheduleSMSForToday() {
        LocalDate todayDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalTime endOfDay = LocalTime.MAX;

        setAllActiveRemindersToPending();
        cancelScheduledSMS();

        try {
            List<MedicationReminder> medicationReminders = medicationReminderService.getPendingMedicationRemindersForToday(currentTime, endOfDay, todayDate);

            log.info("Scheduling {} reminders for today", medicationReminders.size());
            for (MedicationReminder reminder : medicationReminders) scheduleReminder(reminder, todayDate);

        } catch (Exception e) {
            log.error("Failed to schedule SMS for today", e);
        }
    }

    private void setAllActiveRemindersToPending() {
        log.info("Setting all the active reminders to pending");

        List<MedicationReminder> medicationReminders = medicationReminderService.getAllActiveMedicationRemindersForToday(LocalDate.now());
        medicationReminders.forEach(reminder -> reminder.setNotificationStatus("PENDING"));

        for (MedicationReminder reminder : medicationReminders) medicationReminderService.saveReminder(reminder);
    }

    private void scheduleReminder(MedicationReminder reminder, LocalDate todayDate) {
        try {
            // This is just for double verification.
            if (reminder.getMedicationDeadline().isBefore(todayDate)) {
                log.info("Skipping expired reminder: {}", reminder.getReminderId());
                return;
            }

            long delaySeconds = getDelayTimeForReminder(todayDate, reminder);

            ScheduledFuture<?> future = scheduledExecutorService.schedule(() ->
                    processReminder(reminder), delaySeconds, TimeUnit.SECONDS);

            scheduledReminders.put(reminder.getReminderId(), future);
            log.debug("Scheduled reminder {}", reminder.getReminderId());
        } catch (Exception e) {
            log.error("Failed to schedule reminder: {}", reminder.getReminderId(), e);
        }
    }

    private long getDelayTimeForReminder(LocalDate todayDate, MedicationReminder reminder) {
        LocalDateTime medicationDateTime = LocalDateTime.of(todayDate, reminder.getMedicationTime());
        LocalDateTime reminderScheduleTime = medicationDateTime.minusSeconds(NOTIFICATION_LEAD_SECONDS);
        long delaySeconds = Duration.between(LocalDateTime.now(), reminderScheduleTime).getSeconds();

        if (delaySeconds < 0) {
            log.info("Reminder time for reminder id {} has passed. Sending SMS immediately.", reminder.getReminderId());
        }
        return max(0, delaySeconds);
    }

    public void processReminder(MedicationReminder reminder) {
        int attempts = 0;

        String message = "";

        while (attempts < maxRetryAttempts) {
            try {
                smsService.sendSms("Source", reminder.getPatient().getPhone(), message);
                log.info("Processing reminder id:{}", reminder.getReminderId());

                reminder.setNotificationStatus("SENT");
                medicationReminderService.saveReminder(reminder);
                scheduledReminders.remove(reminder.getReminderId());

                return;
            } catch (Exception e) {
                attempts++;

                log.error("Attempt {} failed for reminder {}: {}",
                        attempts, reminder.getReminderId(), e.getMessage());

                if (attempts < maxRetryAttempts) {
                    try {
                        Thread.sleep(retryDelaySeconds * 1000L);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
        reminder.setNotificationStatus("Failed");
        medicationReminderService.saveReminder(reminder);
    }

    @Override
    public void cancelScheduledSMS() {
        log.info("Cancelling {} scheduled reminders", scheduledReminders.size());

        scheduledReminders.forEach((id, future) -> future.cancel(true));
        scheduledReminders.clear();

        log.info("All scheduled reminders cancelled");
    }

    @Override
    public void rescheduleSMS() {
        cancelScheduledSMS();
        scheduleSMSForToday();
    }
}
