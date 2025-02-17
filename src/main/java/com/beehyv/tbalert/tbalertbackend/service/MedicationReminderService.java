package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.MedicationReminderInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MedicationReminderOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.MedicationReminder;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MedicationReminderService {

    List<MedicationReminder> getPendingMedicationRemindersForToday(LocalTime arg1, LocalTime arg2, LocalDate arg3);

    void saveReminder(MedicationReminder medicationReminder);

    List<MedicationReminder> getAllActiveMedicationRemindersForToday(LocalDate today);

    void saveReminderFromInput(MedicationReminderInputDTO medicationReminder);

    List<MedicationReminderOutputDTO> getAllMedicationReminders();

    List<MedicationReminderOutputDTO> getAllActiveMedicationRemindersForOutput();

    List<MedicationReminderOutputDTO> getAllActiveAndPendingMedicationReminders();

    List<MedicationReminderOutputDTO> getActiveMedicationRemindersFilteredByPatientID(Integer patientId);

    List<MedicationReminderOutputDTO> getActiveMedicationRemindersFilteredByMedicationID(Integer medicationId);

    List<MedicationReminderOutputDTO> getActiveMedicationRemindersFilteredByPatientIDAndMedicationID(Integer patientId, Integer medicationId);

    void updateReminder(Long id, @Valid MedicationReminderInputDTO medicationReminder);
}
