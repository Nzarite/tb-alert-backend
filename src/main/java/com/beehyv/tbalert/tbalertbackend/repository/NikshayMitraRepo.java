package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.NikshayMitra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NikshayMitraRepo extends JpaRepository<NikshayMitra, Integer>, JpaSpecificationExecutor<NikshayMitra> {

    Optional<NikshayMitra> findByPatient_Id(String id);

}
