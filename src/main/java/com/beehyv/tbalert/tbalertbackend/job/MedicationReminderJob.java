package com.beehyv.tbalert.tbalertbackend.job;

import com.beehyv.tbalert.tbalertbackend.dao.MedicationReminderDAO;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnBean(PlivoSmsService.class)
public class MedicationReminderJob {

    private final MedicationReminderService medicationReminderService;
    private final TaskScheduler taskScheduler;
    private final Map<String, ScheduledFuture<?>> futureTasks = new ConcurrentHashMap<>();  // <patientId, task>
    private final SettingRepo settingRepo;
    private String cachedSmsTemplate;

    @Value("${app.notification.timezone:Asia/Kolkata}")
    private String timezone;

    public void scheduleMedicationReminderForAllPatients() {
        try {
            // All reminders are fetched from DB
            List<MedicationReminderDAO> reminders = medicationReminderService.getAllMedicationReminders();

            fetchSmsTemplate();
            // Cancel any previous task before scheduling a new one
            cancelAllScheduledTasks();

            int cnt = 0;
            for (MedicationReminderDAO reminder : reminders)
                if (scheduleReminderForPatient(reminder))
                    cnt++;

            log.info("Scheduled {} medication reminders.", cnt);
        } catch (Exception e) {
            log.error("Failed to schedule Medication Reminders: {}", e.getMessage(), e);
        }
    }

    private void fetchSmsTemplate() {
        String newTemplate = settingRepo.findByKeyName("sms_template").getValue();
        if (!newTemplate.equals(cachedSmsTemplate)) {
            cachedSmsTemplate = newTemplate;
        }
    }

    private boolean scheduleReminderForPatient(MedicationReminderDAO reminder) {
        try {
            String timeSetting = reminder.getReminderTime();

            if (timeSetting == null || !timeSetting.matches("\\d{2}:\\d{2}")) {
                log.error("Invalid medication reminder time format for patient: {}", reminder.getPatientId());
                return false;
            }

            LocalDateTime scheduledTime = getSchedulingTimeForReminder(timeSetting);
            if (scheduledTime.isBefore(LocalDateTime.now())) {
                log.warn("Reminder time for patient {} is before current time", reminder.getPatientId());
                return false;
            }
            Instant instant = scheduledTime.atZone(ZoneId.of(timezone)).toInstant();

            ScheduledFuture<?> futureTask = taskScheduler.schedule(() -> execute(reminder), instant);
            futureTasks.put(reminder.getPatientId(), futureTask);

            log.debug("Scheduled reminder for Patient ID {} at {}", reminder.getPatientId(), scheduledTime);

            return true;
        } catch (Exception e) {
            log.error("Failed to schedule reminder for patient {}: {}", reminder.getPatientId(), e.getMessage(), e);
            return false;
        }
    }

    private LocalDateTime getSchedulingTimeForReminder(String timeSetting) {
        // Mapping string to localDateTime
        int hour = Integer.parseInt(timeSetting.split(":")[0]);
        int minute = Integer.parseInt(timeSetting.split(":")[1]);

        // Set the scheduled time for today
        LocalDateTime now = LocalDateTime.now(ZoneId.of(timezone));

        return now.withHour(hour).withMinute(minute).withSecond(0);
    }

    private synchronized void cancelAllScheduledTasks() {
        futureTasks.forEach((patientId, task) -> {
            if (task != null && !task.isCancelled())
                task.cancel(false);
        });
        futureTasks.clear();

        log.info("Cancelled previously scheduled reminders successfully.");
    }

    private synchronized void execute(MedicationReminderDAO reminder) {
        try {
            log.info("Executing Medication Reminder for patient {} at {}", reminder.getPatientId(), LocalDateTime.now());

            if (cachedSmsTemplate == null) fetchSmsTemplate();

            medicationReminderService.sendMedicationReminder(reminder, cachedSmsTemplate);
        } catch (Exception e) {
            log.error("Failed to send reminder to Patient ID {}: {}", reminder.getPatientId(), e.getMessage(), e);
        }
    }
}
