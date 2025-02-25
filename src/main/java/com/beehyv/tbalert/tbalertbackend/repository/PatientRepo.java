package com.beehyv.tbalert.tbalertbackend.repository;

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
}
