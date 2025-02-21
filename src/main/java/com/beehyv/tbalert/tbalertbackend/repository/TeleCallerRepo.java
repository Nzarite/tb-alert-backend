package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.TeleCaller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeleCallerRepo extends JpaRepository<TeleCaller, Long> {

    List<TeleCaller> findByPerson_Address_StateAndPerson_IsDeletedFalse(String personAddressState);

    List<TeleCaller> findByPerson_IsDeletedFalse();
}
