package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.ActivityTrackerInputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.ActivityTracker;
import com.beehyv.tbalert.tbalertbackend.service.ActivityTrackerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity_tracker")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
@PreAuthorize("hasAuthority('ROLE_FieldCoordinator')")
public class ActivityTrackerController {

    private ActivityTrackerService activityTrackerService;

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_SuperAdmin')")
    public ResponseEntity<ActivityTracker> getForm(@PathVariable Long id) {
        return new ResponseEntity<>(activityTrackerService.getForm(id), HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_SuperAdmin')")
    public ResponseEntity<List<ActivityTracker>> getAllForms() {
        return new ResponseEntity<>(activityTrackerService.getAllForms(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> submitForm(@Valid @RequestBody ActivityTrackerInputDTO activityTrackerInputDTO) {
        return new ResponseEntity<>(activityTrackerService.submit(activityTrackerInputDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_SuperAdmin')")
    public ResponseEntity<String> deleteForm(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(activityTrackerService.deleteForm(id), HttpStatus.OK);
    }
}
