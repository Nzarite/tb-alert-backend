package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.MedicationReminderInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MedicationReminderOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.MedicationReminder;
import com.beehyv.tbalert.tbalertbackend.repository.MedicationReminderRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@Slf4j
@AllArgsConstructor
public class MedicationReminderMapper {

    private final PatientMapper patientMapper;
    private final MedicationMapper medicationMapper;
    private final MedicationReminderRepo medicationReminderRepo;

    public MedicationReminder findMedicationReminder(Long medicationReminderId) {
        return medicationReminderRepo.findById(medicationReminderId)
                .orElseThrow(() -> new IllegalArgumentException("MedicationReminder not found for id: " + medicationReminderId));
    }

    public MedicationReminderOutputDTO toMedicationReminderOutputDTO(MedicationReminder medicationReminder) {
        log.info("MedicationReminderMapper::toMedicationReminderOutputDTO");

        return MedicationReminderOutputDTO.builder()
                .medicationName(medicationReminder.getMedication().getName())
                .patient(patientMapper.toPatientOutputDTO(medicationReminder.getPatient()))
                .medicationTime(medicationReminder.getMedicationTime().toString())
                .deadline(medicationReminder.getMedicationDeadline().toString())
                .build();
    }

    public MedicationReminder toMedicationReminder(MedicationReminderInputDTO medicationReminderInputDTO) {
        log.info("MedicationReminderMapper::toMedicationReminder");

        return MedicationReminder.builder()
                .patient(patientMapper.findPatient(medicationReminderInputDTO.getPatientId()))
                .medication(medicationMapper.findMedicationById(medicationReminderInputDTO.getMedicationId()))
                .medicationTime(LocalTime.parse(medicationReminderInputDTO.getMedicationTime()))
                .medicationDeadline(LocalDate.parse(medicationReminderInputDTO.getMedicationDeadline()))
                .build();
    }
}
