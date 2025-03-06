package com.beehyv.tbalert.tbalertbackend.scheduler;

import com.beehyv.tbalert.tbalertbackend.job.MedicationReminderJob;
import com.beehyv.tbalert.tbalertbackend.service.impl.PlivoSmsService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnBean(PlivoSmsService.class)
public class Scheduler {

    private final MedicationReminderJob reminderJob;

    @Scheduled(cron = "1 0 0 * * *", zone = "Asia/Kolkata")
    public void dailyMidnightScheduler() {
        try {
            log.info("Daily Scheduler called at {}", LocalDateTime.now());
            reminderJob.scheduleMedicationReminderForAllPatients();
        } catch (Exception e) {
            log.error("Daily Scheduler failed at {}, error msg: ", LocalDateTime.now(), e);
        }
    }

    @PostConstruct
    public void startupScheduler() {
        log.info("Initializing Medication Reminder Scheduler on startup at {}", LocalDateTime.now());
        reminderJob.scheduleMedicationReminderForAllPatients();
    }
}
