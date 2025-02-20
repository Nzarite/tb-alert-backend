package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.Medication;
import com.beehyv.tbalert.tbalertbackend.entity.MedicationReminder;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface MedicationReminderRepo extends JpaRepository<MedicationReminder, Long> {

    @Query("SELECT m FROM MedicationReminder m " +
            "WHERE m.medicationDeadline >= :today " +
            "AND (m.medicationTime BETWEEN :startOfDay AND :endOfDay " +
            "OR m.notificationStatus = 'PENDING')")
    List<MedicationReminder> findUnsentRemindersForDay(@Param("startOfDay") LocalTime startOfDay,
                                                       @Param("endOfDay") LocalTime endOfDay,
                                                       @Param("today") LocalDate today);

    List<MedicationReminder> findAllByMedicationDeadlineAfter(@Param("today") LocalDate today);

    List<MedicationReminder> findAllByPatientAndMedicationDeadlineAfter(Patient patient, LocalDate today);

    List<MedicationReminder> findAllByMedicationAndMedicationDeadlineAfter(Medication medication, LocalDate today);

    List<MedicationReminder> findAllByMedicationAndPatient(Medication medication, Patient patient);
}
