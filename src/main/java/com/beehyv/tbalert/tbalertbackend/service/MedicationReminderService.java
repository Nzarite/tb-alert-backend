package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.entity.MedicationReminder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MedicationReminderService {

    List<MedicationReminder> getPendingMedicationRemindersForToday(LocalDateTime arg1, LocalDateTime arg2, LocalDate arg3);

    void saveReminder(MedicationReminder medicationReminder);

    List<MedicationReminder> getAllActiveMedicationRemindersForToday(LocalDate today);

}
