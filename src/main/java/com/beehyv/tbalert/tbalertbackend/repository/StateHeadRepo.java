package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.dto.output.StateHeadOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Address;
import com.beehyv.tbalert.tbalertbackend.entity.StateHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StateHeadRepo extends JpaRepository<StateHead, Long> {

//    @Query("SELECT s FROM StateHead s WHERE s.id = :stateId AND s.person.isDeleted = false")
//    Optional<StateHead> findById(@Param("stateId") Long stateId);

    List<StateHead> findByPerson_IsDeletedFalse();

    @Query("SELECT s FROM StateHead s WHERE s.person.isDeleted = FALSE AND (s.person.firstName LIKE %:name% OR s.person.lastName LIKE %:name%)")
    List<StateHead> findAllByPerson_FirstNameContainingIgnoreCaseOrPerson_LastNameContainingIgnoreCase(@Param("name")String name);

    List<StateHead> findAllByPerson_Address_State_StateName(String state);

    List<StateHead> findAllByPerson_IsDeletedTrue();

    List<StateHead> findAllByPerson_IsDeletedTrueAndPerson_Address_State_StateName(String personAddressStateStateName);

    List<StateHead> findAllByPerson_IsDeletedFalseAndPerson_Address_State_StateName(String state);
}
