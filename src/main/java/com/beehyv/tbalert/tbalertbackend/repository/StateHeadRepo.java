package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.StateHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface StateHeadRepo extends JpaRepository<StateHead, Long> {

//    @Query("SELECT s FROM StateHead s WHERE s.id = :stateId AND s.person.isDeleted = false")
//    Optional<StateHead> findById(@Param("stateId") Long stateId);

    List<StateHead> findByPerson_IsDeletedFalse();
    List<StateHead> findAllByPerson_FirstNameContainingIgnoreCaseOrPerson_LastNameContainingIgnoreCase(String name, String name1);
}
