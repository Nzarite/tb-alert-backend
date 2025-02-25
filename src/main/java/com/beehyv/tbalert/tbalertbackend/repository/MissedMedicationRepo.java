package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.MissedMedication;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface MissedMedicationRepo extends JpaRepository<MissedMedication, Integer> {


    List<MissedMedication> findAllByPatientMedication(PatientMedication patientMedication);

    MissedMedication findByPatientMedication(PatientMedication patientMedication);


    List<MissedMedication> findAllByPatientMedicationInAndDate(List<PatientMedication> patientMedication, LocalDate date);

    List<MissedMedication> findByPatientMedicationAndDate(PatientMedication patientMedication, LocalDate date);

    List<MissedMedication> findByPatientMedicationInAndDate(ArrayList<PatientMedication> patientMedications, LocalDate date);

    List<MissedMedication> findAllByPatientMedicationInAndDateIn(List<PatientMedication> patientMedications, List<LocalDate> list);
}
