package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.ContactScreening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactScreeningRepository extends JpaRepository<ContactScreening, Integer> {
    ContactScreening findByPatientId(Integer patientId);

    void deleteByPatientId(int patientId);
}
