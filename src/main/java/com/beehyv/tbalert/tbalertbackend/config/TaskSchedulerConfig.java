package com.beehyv.tbalert.tbalertbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class TaskSchedulerConfig {

    @Value("${app.scheduler.threadpool-size}")
    private int maxAllowedThreads;

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(maxAllowedThreads);
        scheduler.setThreadNamePrefix("MedicationReminder-");
        scheduler.initialize();
        return scheduler;
    }
}
