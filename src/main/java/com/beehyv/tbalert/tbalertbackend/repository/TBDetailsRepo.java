package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.TBDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TBDetailsRepo extends JpaRepository<TBDetails, Integer>, JpaSpecificationExecutor<TBDetails> {
    Optional<TBDetails> findByPatient_Id(String patientId);
}
