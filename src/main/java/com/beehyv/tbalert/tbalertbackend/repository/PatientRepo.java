package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import org.apache.el.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepo extends JpaRepository<Patient, String>, JpaSpecificationExecutor<Patient> {

    int countByCurrentStatusAndPerson_IsDeletedFalse(String currentStatus);

    List<Patient> findAllByPerson_FirstNameContainingIgnoreCaseOrPerson_LastNameContainingIgnoreCaseAndPerson_IsDeletedFalse(String patientName, String patientName1);

    @Query(value = "SELECT p FROM Patient p LEFT JOIN NikshayMitra n " +
            "ON p.id = n.patient.id " +
            "WHERE p.person.isDeleted = false AND (" +
            "LOWER(p.person.firstName) LIKE LOWER(CONCAT('%', :patientName, '%')) " +
            "OR LOWER(p.person.lastName) LIKE LOWER(CONCAT('%', :patientName, '%')) " +
            "OR LOWER(p.id) LIKE LOWER(CONCAT('%', :patientName, '%')) " +
            "OR LOWER(n.nikshayId) LIKE LOWER(CONCAT('%', :patientName, '%')))")
    List<Patient> findAllByPatientIdOrNameOrNikshayId(@Param("patientName") String patientName);

    List<Patient> findAllByPerson_IsDeletedFalse();

    List<Patient> findAllByPerson_Address_State_StateNameAndPerson_IsDeletedFalse(String state);

    Optional<Patient> findByIdAndPerson_IsDeletedFalse(String patientId);
}
