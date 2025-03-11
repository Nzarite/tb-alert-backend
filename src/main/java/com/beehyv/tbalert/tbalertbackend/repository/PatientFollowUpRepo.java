package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.PatientFollowUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Repository
public interface PatientFollowUpRepo extends JpaRepository<PatientFollowUp, Integer> {

    List<PatientFollowUp> findByPatient_Id(String id);

    PatientFollowUp findByPatientAndDate(Patient patient, LocalDate date);

    List<PatientFollowUp> findByPatientAndDateBefore(Patient patient, LocalDate dateBefore);

    List<PatientFollowUp> findAllByPatient_Id(String patientId);

    void deleteAllByPatient_Id(String patientId);

    List<PatientFollowUp> findAllByDateLessThanEqualAndPatient_IdInAndStatus(LocalDate dateIsLessThan, Collection<String> patientIds, String status);
}
