package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.NikshayMitra;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NikshayMitraRepo extends JpaRepository<NikshayMitra, Integer> {
    Optional<NikshayMitra> findByPatient_Id(String id);

    Optional<NikshayMitra> findByNikshayId(String nikshayId);
}
