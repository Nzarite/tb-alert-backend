package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.dao.MedicationReminderProjection;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepo extends JpaRepository<Patient, String>, JpaSpecificationExecutor<Patient> {

    int countByCurrentStatus(String currentStatus);

    @Query(value = "SELECT p FROM Patient p LEFT JOIN NikshayMitra n " +
            "ON p.id = n.patient.id " +
            "WHERE LOWER(p.person.firstName) LIKE LOWER(CONCAT('%', :patientName, '%')) " +
            "OR LOWER(p.person.lastName) LIKE LOWER(CONCAT('%', :patientName, '%')) " +
            "OR LOWER(p.id) LIKE LOWER(CONCAT('%', :patientName, '%')) " +
            "OR LOWER(n.nikshayId) LIKE LOWER(CONCAT('%', :patientName, '%'))")
    List<Patient> findAllByPatientIdOrNameOrNikshayId(@Param("patientName") String patientName);

    @Query(value = """
                SELECT 
                    p.id AS patientId,
                    nm.nikshay_id AS nikshayId,
                    per.first_name AS firstName,
                    per.last_name AS lastName,
                    per.phone_number AS phoneNumber,
                    MAX(pf.date) AS lastFollowupDate,
                    m.id AS medicationId,
                    m.name AS medicationName
                FROM patient p
                JOIN person per ON p.person_id = per.id
                JOIN patient_follow_up pf ON p.id = pf.patient_id
                JOIN patient_medication pm ON p.id = pm.patient_id
                JOIN medication m ON pm.medication_id = m.id
                LEFT JOIN nikshay_mitra nm ON p.id = nm.patient_id
                WHERE p.cured = 0 AND p.current_status = 'alive' AND p.consent_for_message = true
                GROUP BY p.id, nm.nikshay_id, per.first_name, per.last_name, per.phone_number, m.id, m.name
                HAVING MAX(pf.date) > CURRENT_DATE
            """, nativeQuery = true)
    List<MedicationReminderProjection> findAllMedicationReminders();

}
