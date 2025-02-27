package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.TeleCaller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeleCallerRepo extends JpaRepository<TeleCaller, Long> {

    List<TeleCaller> findByPerson_Address_State_StateNameAndPerson_IsDeletedFalse(String personAddressState);

    List<TeleCaller> findAllByPerson_IsDeletedFalse();

    List<TeleCaller> findAllByPerson_FirstNameContainingIgnoreCaseOrPerson_LastNameContainingIgnoreCaseAndPerson_IsDeletedFalse(String name, String name1);
}
