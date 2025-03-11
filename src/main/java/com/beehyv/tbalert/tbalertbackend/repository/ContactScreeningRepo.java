package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.ContactScreening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ContactScreeningRepo extends JpaRepository<ContactScreening, Integer> {

    Optional<ContactScreening> findByPatient_Id(String patientId);

}
