package com.beehyv.tbalert.tbalertbackend.dao;

public interface MedicationReminderProjection {
    String getPatientId();

    String getNikshayId();

    String getFirstName();

    String getLastName();

    String getReminderTime();

    String getPhoneNumber();

    String getLastFollowupDate();

    String getMedicationId();

    String getMedicationName();
}
