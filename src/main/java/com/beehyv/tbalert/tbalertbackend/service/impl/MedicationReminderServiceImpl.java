package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.entity.MedicationReminder;
import com.beehyv.tbalert.tbalertbackend.repository.MedicationReminderRepo;
import com.beehyv.tbalert.tbalertbackend.service.MedicationReminderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class MedicationReminderServiceImpl implements MedicationReminderService {

    private final MedicationReminderRepo medicationReminderRepo;

    @Override
    public List<MedicationReminder> getPendingMedicationRemindersForToday(LocalDateTime startOfDay, LocalDateTime endOfDay, LocalDate todayDate) {
        return medicationReminderRepo.findUnsentRemindersForDay(startOfDay, endOfDay, todayDate);
    }

    @Override
    public void saveReminder(MedicationReminder medicationReminder) {
        medicationReminderRepo.save(medicationReminder);
    }

    @Override
    public List<MedicationReminder> getAllActiveMedicationRemindersForToday(LocalDate today) {
        return medicationReminderRepo.findAllByMedicationDeadlineAfter(today);
    }
}
