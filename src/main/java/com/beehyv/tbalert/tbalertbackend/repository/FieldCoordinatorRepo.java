package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.FieldCoordinator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FieldCoordinatorRepo extends JpaRepository<FieldCoordinator, Long> {

    @Query("SELECT t FROM FieldCoordinator t WHERE t.person.isDeleted = FALSE AND (t.person.firstName LIKE %:name% OR t.person.lastName LIKE %:name%)")
    List<FieldCoordinator> findAllByPerson_FirstNameContainingIgnoreCaseOrPerson_LastNameContainingIgnoreCase(String name);
}
