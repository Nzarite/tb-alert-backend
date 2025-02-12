package com.beehyv.tbalert.tbalertbackend.scheduler;

import com.beehyv.tbalert.tbalertbackend.service.SMSSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SMSScheduler {
    private final SMSSchedulerService smsSchedulerService;

    // This will instruct whether to run the backup scheduler or not
    private boolean schedulingSuccess;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Kolkata")
    public void scheduleDailySMS() {
        try {
            log.info("Daily SMS Scheduler called at {}", LocalDateTime.now());

            schedulingSuccess = false;
            smsSchedulerService.scheduleSMSForToday();
            schedulingSuccess = true;

            log.info("Daily SMS Scheduled successfully at {}", LocalDateTime.now());
        } catch (Exception e) {
            log.error("Daily Scheduler failed at {}, error msg: ", LocalDateTime.now(), e);
        }
    }

    @Scheduled(cron = "0 30 0 * * *", zone = "Asia/Kolkata")  // Runs at 00:30 AM
    public void backupScheduler() {
        try {
            if (!schedulingSuccess) {
                log.info("Backup Scheduler called at {}", LocalDateTime.now());

                smsSchedulerService.rescheduleSMS();
                schedulingSuccess = true;

                log.info("Daily SMS Scheduled successfully by backup scheduler at {}", LocalDateTime.now());
            }
        } catch (Exception e) {
            log.error("Backup Scheduler failed at {}, error message: ", LocalDateTime.now(), e);
        }
    }
}
