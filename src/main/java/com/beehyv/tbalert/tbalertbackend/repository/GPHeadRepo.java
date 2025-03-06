package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.GPHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GPHeadRepo extends JpaRepository<GPHead, Long> {
    @Query("SELECT t FROM GPHead t WHERE t.person.isDeleted = FALSE AND (t.person.firstName LIKE %:name% OR t.person.lastName LIKE %:name%)")
    List<GPHead> findAllByPerson_FirstNameContainingIgnoreCaseOrPerson_LastNameContainingIgnoreCase(String name);

    @Query("""
                SELECT t FROM GPHead t
                WHERE (LOWER(t.person.firstName) LIKE LOWER(CONCAT('%', :name, '%'))
                   OR LOWER(t.person.lastName) LIKE LOWER(CONCAT('%', :name, '%')))
                  AND t.person.address.state.stateName = :state
                  AND t.person.isDeleted = false
            """)
    List<GPHead> findGPHeadByNameAndState(@Param("name") String name, @Param("state") String state);

    List<GPHead> findByPerson_Address_State_StateNameAndPerson_IsDeletedFalse(String personAddressState);
}
