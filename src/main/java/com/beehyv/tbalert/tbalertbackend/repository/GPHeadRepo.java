package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.GPHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GPHeadRepo extends JpaRepository<GPHead, Long> {

    @Query("SELECT t FROM GPHead t WHERE t.person.isDeleted = FALSE AND (t.person.firstName LIKE %:name% OR t.person.lastName LIKE %:name%)")
    List<GPHead> findAllByPerson_FirstNameContainingIgnoreCaseOrPerson_LastNameContainingIgnoreCase(String name);
}
