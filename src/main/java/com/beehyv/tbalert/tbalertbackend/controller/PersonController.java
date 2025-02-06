package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.PersonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/person")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
public class PersonController {

    private final PersonService personService;

    @PostMapping("")
    public ResponseEntity<PersonOutputDTO> createPerson(@RequestBody @Valid PersonInputDTO person) {
      log.info("Controller called for Creating person: {}", person);
      try{
          return new ResponseEntity<>(personService.add(person), HttpStatus.CREATED);
      } catch (Exception e) {
          log.error("Controller called for Creating person: {}", person, e);
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PersonOutputDTO>> getAllPersons() {
        log.info("Controller called for Getting all persons");
        try {
            return new ResponseEntity<>(personService.getAll(),HttpStatus.OK);
        } catch (Exception e) {
            log.error("Controller called for Getting all persons", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonOutputDTO> getPersonById(@PathVariable Long id) {
        log.info("Controller called for Getting person by id: {}", id);
        try{
            return new ResponseEntity<PersonOutputDTO>(personService.get(id),HttpStatus.OK);
        } catch (Exception e) {
            log.error("Controller called for Getting person by id: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
