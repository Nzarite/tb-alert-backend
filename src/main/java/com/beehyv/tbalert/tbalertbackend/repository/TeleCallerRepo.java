package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.TeleCaller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeleCallerRepo extends JpaRepository<TeleCaller, Long> {

    @Query("SELECT t FROM TeleCaller t " +
            "JOIN t.person p " +
            "JOIN Address a ON p.id = a.person.id " +
            "WHERE a.state = :state")
    List<TeleCaller> findByTeleCallerByState(@Param("state") String name);
}
