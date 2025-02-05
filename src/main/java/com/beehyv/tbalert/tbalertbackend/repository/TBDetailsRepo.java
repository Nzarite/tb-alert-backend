package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.TBDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TBDetailsRepo extends JpaRepository<TBDetails, Integer> {
    Optional<TBDetails> findByPatient_Id(Integer patientId);
}
