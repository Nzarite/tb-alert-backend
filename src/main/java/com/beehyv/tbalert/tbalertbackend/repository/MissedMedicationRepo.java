package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.MissedMedication;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MissedMedicationRepo extends JpaRepository<MissedMedication, Integer> {


    List<MissedMedication> findAllByPatientMedication(PatientMedication patientMedication);

    MissedMedication findByPatientMedication(PatientMedication patientMedication);

    List<MissedMedication> findByPatientMedicationAndDate(PatientMedication patientMedication, @NotEmpty(message = "Empty date") LocalDate date);
}
