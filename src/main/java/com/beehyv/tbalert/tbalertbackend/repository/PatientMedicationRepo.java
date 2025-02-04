package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.Medication;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientMedicationRepo extends JpaRepository<PatientMedication, Integer> {

    List<PatientMedication> id(int id);

    List<PatientMedication> getPatientMedicationsByPatient(Patient patient);

    PatientMedication findPatientMedicationByPatientAndMedication(Patient patient, Medication medication);

    List<PatientMedication> findPatientMedicationByPatient(Patient patient);
}
