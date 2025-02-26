package com.beehyv.tbalert.tbalertbackend.job;

import com.beehyv.tbalert.tbalertbackend.repository.SettingRepo;
import com.beehyv.tbalert.tbalertbackend.service.impl.MedicationReminderService;
import com.beehyv.tbalert.tbalertbackend.service.impl.PlivoSmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.ScheduledFuture;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnBean(PlivoSmsService.class)
public class MedicationReminderJob {

    private final MedicationReminderService medicationReminderService;
    private final SettingRepo settingRepo;
    private final TaskScheduler taskScheduler;
    private ScheduledFuture<?> futureTasks;

    @Value("${app.notification.timezone:Asia/Kolkata}")
    private String timezone;

    public void scheduleMedicationReminder() {
        try {
            String timeSetting = settingRepo.findByKeyName("sms_reminder_time") != null
                    ? settingRepo.findByKeyName("sms_reminder_time").getValue()
                    : "08:00"; // Default to 8:00 AM if not set

            if (timeSetting == null || !timeSetting.matches("\\d{2}:\\d{2}")) {
                log.error("Invalid medication reminder time format in DB");
                return;
            }

            LocalDateTime scheduledTime = getNextScheduledTime(timeSetting);
            Instant instant = scheduledTime.atZone(ZoneId.of(timezone)).toInstant();

            // Cancel any previous task before scheduling a new one
            cancelFutureTasks();

            futureTasks = taskScheduler.schedule(this::execute, instant);

            log.info("Medication Reminders scheduled at: {}", scheduledTime.atZone(ZoneId.of(timezone)));
        } catch (Exception e) {
            log.error("Failed to schedule Medication Reminder: {}", e.getMessage(), e);
        }
    }

    private LocalDateTime getNextScheduledTime(String timeSetting) {
        int hour = Integer.parseInt(timeSetting.split(":")[0]);
        int minute = Integer.parseInt(timeSetting.split(":")[1]);

        // Set the scheduled time for today
        LocalDateTime now = LocalDateTime.now(ZoneId.of(timezone));
        LocalDateTime scheduledTime = now.withHour(hour).withMinute(minute).withSecond(0);

        // If the scheduled time has already passed today, move to the next day
        if (scheduledTime.isBefore(now)) {
            scheduledTime = scheduledTime.plusDays(1);
        }

        return scheduledTime;
    }

    // Ensures only one task can cancel another at a time.
    private synchronized void cancelFutureTasks() {
        if (futureTasks != null && !futureTasks.isCancelled()) {
            log.info("Cancelling previously scheduled medication reminder.");
            futureTasks.cancel(false);
        }
    }

    private void execute() {
        try {
            log.info("Executing Medication Reminder at {}", LocalDateTime.now());
            medicationReminderService.sendMedicationReminder();
        } catch (Exception e) {
            log.error("Medication Reminder Scheduler failed at {}, error: ", LocalDateTime.now(), e);
        }
    }
}
