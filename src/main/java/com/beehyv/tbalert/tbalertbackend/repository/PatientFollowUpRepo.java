package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputForFrontEndDto;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.PatientFollowUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface PatientFollowUpRepo extends JpaRepository<PatientFollowUp, Integer> {

    List<PatientFollowUp> findByPatient_Id(String id);

    List<PatientFollowUp> findByDate(LocalDate date);

    PatientFollowUp findByPatientAndDate(Patient patient, LocalDate date);

    List<PatientFollowUp> findByPatientAndDateBefore(Patient patient, LocalDate dateBefore);

    List<PatientFollowUp> findAllByPatient_Id(String patientId);

    void deleteAllByPatient_Id(String patientId);

    List<PatientFollowUp> findAllByDate(LocalDate date);

    List<PatientFollowUp> findAllByDateAndPatient_IdIn(LocalDate now, List<String> patientIds);
}
