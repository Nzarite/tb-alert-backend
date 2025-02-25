package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {
    Person findByEmail(String email);

    List<Person> findByAddress_State(String state);

    int countByCreatedBy(String createdBy);

    boolean existsByEmail(String email);
}
