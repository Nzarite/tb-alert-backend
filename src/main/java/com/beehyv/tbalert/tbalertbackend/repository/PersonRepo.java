package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {
    Person findByEmail(String email);

    @Query("SELECT a.person FROM Address a WHERE a.state=:state")
    List<Person> findByState(@Param("state") String state);
}
