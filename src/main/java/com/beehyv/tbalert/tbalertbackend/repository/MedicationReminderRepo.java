package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.MedicationReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MedicationReminderRepo extends JpaRepository<MedicationReminder, Long> {
    @Query("SELECT m FROM MedicationReminder m WHERE "
            + "((m.medicationTime BETWEEN :startOfDay AND :endOfDay)"
            + " AND m.medicationDeadline >= :today) "
            + "OR m.notificationStatus = 'Pending'")
    List<MedicationReminder> findUnsentRemindersForDay(@Param("startOfDay") LocalDateTime startOfDay,
                                                       @Param("endOfDay") LocalDateTime endOfDay,
                                                       @Param("today") LocalDate today);

    List<MedicationReminder> findAllByMedicationDeadlineAfter(@Param("today") LocalDate today);

//    List<MedicationReminder> findAllByNotificationStatus(String status);
}
