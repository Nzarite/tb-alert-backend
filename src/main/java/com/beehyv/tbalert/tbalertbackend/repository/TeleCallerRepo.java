package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.dto.output.TeleCallerOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.TeleCaller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TeleCallerRepo extends JpaRepository<TeleCaller, Long> {

    List<TeleCaller> findByPerson_Address_State_StateNameAndPerson_IsDeletedFalse(String personAddressState);

    List<TeleCaller> findAllByPerson_IsDeletedFalse();

    List<TeleCaller> findAllByPerson_FirstNameContainingIgnoreCaseOrPerson_LastNameContainingIgnoreCaseAndPerson_IsDeletedFalse(String name, String name1);

    List<TeleCaller> findAllByPerson_Address_State_StateName(String personAddressStateStateName);

    List<TeleCaller> findAllByPerson_IsDeletedTrue();

    List<TeleCaller> findAllByPerson_IsDeletedTrueAndPerson_Address_State_StateName(String state);
}
