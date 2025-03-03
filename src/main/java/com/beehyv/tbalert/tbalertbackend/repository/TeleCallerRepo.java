package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.dto.output.TeleCallerOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.TeleCaller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeleCallerRepo extends JpaRepository<TeleCaller, Long> {

    List<TeleCaller> findByPerson_Address_State_StateNameAndPerson_IsDeletedFalse(String personAddressState);

    List<TeleCaller> findAllByPerson_IsDeletedFalse();

    List<TeleCaller> findAllByPerson_FirstNameContainingIgnoreCaseOrPerson_LastNameContainingIgnoreCaseAndPerson_IsDeletedFalse(String name, String name1);

    List<TeleCaller> findAllByPerson_Address_State_StateName(String personAddressStateStateName);

    List<TeleCaller> findAllByPerson_IsDeletedTrue();

    List<TeleCaller> findAllByPerson_IsDeletedTrueAndPerson_Address_State_StateName(String state);

    @Query("""
                SELECT t FROM TeleCaller t
                WHERE (LOWER(t.person.firstName) LIKE LOWER(CONCAT('%', :name, '%'))
                   OR LOWER(t.person.lastName) LIKE LOWER(CONCAT('%', :name, '%')))
                  AND t.person.address.state.stateName = :state
                  AND t.person.isDeleted = false
            """)
    List<TeleCaller> findTeleCallerByNameAndState(@Param("name") String name, @Param("state") String state);


    @Query("SELECT t FROM TeleCaller t WHERE t.person.isDeleted = FALSE AND (t.person.firstName LIKE %:name% OR t.person.lastName LIKE %:name%)")
    List<TeleCaller> findAllByPerson_IsDeletedFalseAndPerson_FirstNameContainingIgnoreCaseOrPerson_LastNameContainingIgnoreCase(@Param("name") String name);
}
