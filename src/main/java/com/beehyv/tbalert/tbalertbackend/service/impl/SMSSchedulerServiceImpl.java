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

@Service
@RequiredArgsConstructor
@Slf4j
public class SMSSchedulerServiceImpl implements SMSSchedulerService {
    @Value("${app.notification.lead-time-seconds:1800}") // 30 minutes default
    private long NOTIFICATION_LEAD_SECONDS;

    @Value("${app.notification.sender-id}")
    private String senderId;

    @Value("${app.notification.threadPoolSize:10}")
    private int threadPoolSize;

    private final PlivoSmsService smsService;
    private final MedicationReminderService medicationReminderService;

    // Required to manage SMS scheduling for the day
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(threadPoolSize);
    private final Map<Long, ScheduledFuture<?>> scheduledReminders = new ConcurrentHashMap<>();

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

            for (MedicationReminder reminder : medicationReminders)
                scheduleReminder(reminder, todayDate);
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

            // This is to instruct a function to run after some seconds
            ScheduledFuture<?> future = scheduledExecutorService.schedule(() ->
                    processReminder(reminder), delaySeconds, TimeUnit.SECONDS);
            scheduledReminders.put(reminder.getReminderId(), future);

            log.debug("Scheduled reminder: {}", reminder.getReminderId());
        } catch (Exception e) {
            log.error("Failed to schedule reminder: {}", reminder.getReminderId(), e);
        }
    }

    private long getDelayTimeForReminder(LocalDate todayDate, MedicationReminder reminder) {
        LocalDateTime reminderScheduleTime = LocalDateTime.of(todayDate, reminder.getMedicationTime());
//        LocalDateTime reminderScheduleTime = medicationDateTime.minusSeconds(NOTIFICATION_LEAD_SECONDS);

        long delaySeconds = Duration.between(LocalDateTime.now(), reminderScheduleTime).getSeconds();
        return Math.max(0, delaySeconds);
    }

    public void processReminder(MedicationReminder reminder) {
        String message = generateReminderMessage(reminder);

        smsService.sendSms(senderId, reminder.getPatient().getPerson().getPhoneNumber(), message);
        log.info("Processing reminder id:{}", reminder.getReminderId());

        reminder.setNotificationStatus("SENT");
        medicationReminderService.saveReminder(reminder);
        scheduledReminders.remove(reminder.getReminderId());
    }

    private String generateReminderMessage(MedicationReminder reminder) {
        return String.format(
                "Hello %s %s, this is a reminder to take your medication: %s at %s. Stay healthy!",
                reminder.getPatient().getPerson().getFirstName(),
                reminder.getPatient().getPerson().getLastName(),
                reminder.getMedication().getName(),
                reminder.getMedicationTime()
        );
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
