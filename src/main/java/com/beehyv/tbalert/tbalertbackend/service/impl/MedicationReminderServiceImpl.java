package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.MedicationReminderInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MedicationReminderOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.MedicationReminder;
import com.beehyv.tbalert.tbalertbackend.mapper.MedicationMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.MedicationReminderMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMapper;
import com.beehyv.tbalert.tbalertbackend.repository.MedicationReminderRepo;
import com.beehyv.tbalert.tbalertbackend.service.MedicationReminderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class MedicationReminderServiceImpl implements MedicationReminderService {

    private final MedicationReminderRepo medicationReminderRepo;
    private final MedicationReminderMapper medicationReminderMapper;
    private final PatientMapper patientMapper;
    private final MedicationMapper medicationMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MedicationReminder> getPendingMedicationRemindersForToday(LocalTime startOfDay, LocalTime endOfDay, LocalDate todayDate) {
        return medicationReminderRepo.findUnsentRemindersForDay(startOfDay, endOfDay, todayDate);
    }

    @Override
    @Transactional
    public void saveReminder(MedicationReminder medicationReminder) {
        medicationReminderRepo.save(medicationReminder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicationReminder> getAllActiveMedicationRemindersForToday(LocalDate today) {
        return medicationReminderRepo.findAllByMedicationDeadlineAfter(today);
    }

    @Override
    @Transactional
    public void saveReminderFromInput(MedicationReminderInputDTO medicationReminder) {
        MedicationReminder medicationReminder1 = medicationReminderMapper.toMedicationReminder(medicationReminder);
        saveReminder(medicationReminder1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicationReminderOutputDTO> getAllMedicationReminders() {
        List<MedicationReminder> medicationReminders = medicationReminderRepo.findAll();

        return medicationReminders.stream()
                .map(medicationReminderMapper::toMedicationReminderOutputDTO)
                .collect(Collectors.toList());
    }

    // To be implemented
    @Override
    @Transactional(readOnly = true)
    public List<MedicationReminderOutputDTO> getAllActiveMedicationRemindersForOutput() {
        List<MedicationReminder> activeReminders = medicationReminderRepo.findAllByMedicationDeadlineAfter(LocalDate.now());

        return activeReminders.stream()
                .map(medicationReminderMapper::toMedicationReminderOutputDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicationReminderOutputDTO> getAllActiveAndPendingMedicationReminders() {
        List<MedicationReminder> medicationReminders = medicationReminderRepo.findUnsentRemindersForDay(LocalTime.now(), LocalTime.MAX, LocalDate.now());

        return medicationReminders.stream()
                .map(medicationReminderMapper::toMedicationReminderOutputDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicationReminderOutputDTO> getActiveMedicationRemindersFilteredByPatientID(String patientId) {
        List<MedicationReminder> medicationReminders = medicationReminderRepo.findAllByPatientAndMedicationDeadlineAfter(patientMapper.findPatient(patientId), LocalDate.now());

        return medicationReminders.stream()
                .map(medicationReminderMapper::toMedicationReminderOutputDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicationReminderOutputDTO> getActiveMedicationRemindersFilteredByMedicationID(Integer medicationId) {
        List<MedicationReminder> medicationReminders = medicationReminderRepo.findAllByMedicationAndMedicationDeadlineAfter(medicationMapper.findMedicationById(medicationId), LocalDate.now());

        return medicationReminders.stream()
                .map(medicationReminderMapper::toMedicationReminderOutputDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicationReminderOutputDTO> getActiveMedicationRemindersFilteredByPatientIDAndMedicationID(String patientId, Integer medicationId) {
        List<MedicationReminder> medicationReminders = medicationReminderRepo.findAllByMedicationAndPatient(medicationMapper.findMedicationById(medicationId), patientMapper.findPatient(patientId));

        return medicationReminders.stream()
                .map(medicationReminderMapper::toMedicationReminderOutputDTO)
                .toList();
    }

    @Override
    @Transactional
    public void updateReminder(Long id, MedicationReminderInputDTO medicationReminderInputDTO) {
        MedicationReminder medicationReminder = medicationReminderMapper.findMedicationReminder(id);

        medicationReminder.setMedication(medicationMapper.findMedicationById(medicationReminderInputDTO.getMedicationId()));
        medicationReminder.setMedicationTime(LocalTime.parse(medicationReminderInputDTO.getMedicationTime()));
        medicationReminder.setMedicationDeadline(LocalDate.parse(medicationReminderInputDTO.getMedicationDeadline()));
        medicationReminder.setPatient(patientMapper.findPatient(medicationReminderInputDTO.getPatientId()));

        saveReminder(medicationReminder);
    }
}
