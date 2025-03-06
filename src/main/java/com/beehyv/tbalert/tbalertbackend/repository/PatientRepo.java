package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.dao.MedicationReminderProjection;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepo extends JpaRepository<Patient, String>, JpaSpecificationExecutor<Patient> {

    int countByCurrentStatusAndPerson_IsDeletedFalse(String currentStatus);

    @Query("""
             SELECT p FROM Patient p
             LEFT JOIN NikshayMitra n ON p.id = n.patient.id
             WHERE p.person.isDeleted = false
             AND (
                 LOWER(p.person.firstName) LIKE LOWER(CONCAT('%', :patientName, '%'))
                 OR LOWER(p.person.lastName) LIKE LOWER(CONCAT('%', :patientName, '%'))
                 OR LOWER(CAST(p.id AS string)) LIKE LOWER(CONCAT('%', :patientName, '%'))
                 OR LOWER(n.nikshayId) LIKE LOWER(CONCAT('%', :patientName, '%'))
             )
             AND (:onlyDiagnosedWithTB = false OR p.isDiagnosedWithTB = true)
            """)
    List<Patient> findAllByPatientIdOrNameOrNikshayId(@Param("patientName") String patientName,
                                                      @Param("onlyDiagnosedWithTB") boolean onlyDiagnosedWithTB);


    @Query(value = """
                 SELECT
                     p.id AS patientId,
                     nm.nikshay_id AS nikshayId,
                     per.first_name AS firstName,
                     per.last_name AS lastName,
                     p.reminder_time AS reminderTime,
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
                 WHERE p.cured = FALSE AND p.current_status = 'alive' AND p.consent_for_message = true AND p.is_diagnosed_with_TB = TRUE
                 GROUP BY p.id, nm.nikshay_id, per.first_name, per.last_name, per.phone_number, m.id, m.name
                 HAVING MAX(pf.date) > CURRENT_DATE
            \s""", nativeQuery = true)
    List<MedicationReminderProjection> findAllMedicationReminders();

    List<Patient> findAllByPerson_IsDeletedFalse();

    List<Patient> findAllByPerson_Address_State_StateNameAndPerson_IsDeletedFalse(String state);

    Optional<Patient> findByIdAndPerson_IsDeletedFalse(String patientId);

    @Query("""
                SELECT p FROM Patient p
                WHERE (LOWER(p.person.firstName) LIKE LOWER(CONCAT('%', :name, '%'))
                   OR LOWER(p.person.lastName) LIKE LOWER(CONCAT('%', :name, '%')))
                  AND p.person.address.state.stateName = :state
                  AND p.person.isDeleted = false
            """)
    List<Patient> findPatientByNameAndState(@Param("name") String name, @Param("state") String state);
}
