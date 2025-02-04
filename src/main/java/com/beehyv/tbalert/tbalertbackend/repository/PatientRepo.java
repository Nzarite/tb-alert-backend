package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer> {
    List<Patient> findAllByFirstNameContainingOrLastNameContaining(String firstName, String lastName);


//    List<PatientOutputDTO> findAllByFirstNameContaining(String patientName);
}
