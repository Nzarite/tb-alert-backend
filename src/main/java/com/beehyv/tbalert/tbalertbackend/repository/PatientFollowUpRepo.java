package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.PatientFollowUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface PatientFollowUpRepo extends JpaRepository<PatientFollowUp, Integer> {

    List<PatientFollowUp> findByPatient_Id(int id);

    PatientFollowUp findByDate(LocalDate date);
}
