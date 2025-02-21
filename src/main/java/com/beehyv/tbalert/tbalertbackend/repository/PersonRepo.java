package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {
    Optional<Person> findByEmailAndIsDeletedFalse(String email);

    List<Person> findByAddress_StateAndIsDeletedFalse(String state);

    int countByCreatedByAndIsDeletedFalse(String createdBy);

    Optional<Person> findByIdAndIsDeletedFalse(Long id);

    List<Person> findAllByIsDeletedFalse();
}
