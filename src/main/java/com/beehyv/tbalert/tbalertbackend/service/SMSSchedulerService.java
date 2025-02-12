package com.beehyv.tbalert.tbalertbackend.service;

public interface SMSSchedulerService {

    void scheduleSMSForToday();

    void cancelScheduledSMS();

    void rescheduleSMS();
}
