package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.PersonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/person")
@PreAuthorize("hasAuthority('ROLE_Telecaller')")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
public class PersonController {

    private final PersonService personService;

    @PostMapping("")
    public ResponseEntity<PersonOutputDTO> createPerson(@RequestBody @Valid PersonInputDTO person) {
      log.info("Controller called for Creating person: {}", person);
          return new ResponseEntity<>(personService.add(person), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PersonOutputDTO>> getAllPersons() {
        log.info("Controller called for Getting all persons");
            return new ResponseEntity<>(personService.getAll(),HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonOutputDTO> getPersonById(@PathVariable Long id) {
        log.info("Controller called for Getting person by id: {}", id);
        return new ResponseEntity<>(personService.get(id),HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<PersonOutputDTO> getPersonByEmail(@RequestBody String email) {
        log.info("Controller called for Getting person by email: {}", email);
        return new ResponseEntity<>(personService.getByEmail(email),HttpStatus.OK);
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<PersonOutputDTO>> getPersonByState(@PathVariable String state) {
        log.info("Controller called for Getting person by state: {}", state);
        return new ResponseEntity<>(personService.getByState(state),HttpStatus.OK);
    }

}
