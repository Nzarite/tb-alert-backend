package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.GramPanchayat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GramPanchayatRepo extends JpaRepository<GramPanchayat, Long> {
    List<GramPanchayat> findAllByMandal_Id(Long mandalId);

}
