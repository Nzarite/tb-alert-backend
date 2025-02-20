package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.MedicationReminderInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MedicationReminderOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.MedicationReminderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reminders")
@RequiredArgsConstructor
@Slf4j
public class MedicationReminderController {

    private final MedicationReminderService medicationReminderService;

    /*

     * 1. Get All Reminders (GET = /reminders) (To be deprecated)
     * 2. Get All Active Reminders (GET = /reminders?status=all)
     * 3. Get All Active & Pending Reminders (GET =/reminders?status=pending)
     * 4. Get All Reminders filtered by patient (GET = /reminders/patient/{patientID})
     * 5. Get All Reminders filtered by medicine (GET = /reminders/medication/{medicationID})
     * 6. Get Single Reminder filtered by patient and medicine (GET = /reminders/patient/{patientID}/medication/{medicationID})
     */

    @GetMapping
    public ResponseEntity<List<MedicationReminderOutputDTO>> getAllMedicationReminders(@RequestParam(name = "status", required = false) String status) {
        log.info("Getting all medication reminders at {}", LocalDateTime.now());

        if (Objects.equals(status.toLowerCase(), "pending"))
            return ResponseEntity.ok(medicationReminderService.getAllActiveAndPendingMedicationReminders());

        if (Objects.equals(status.toLowerCase(), "all"))
            return ResponseEntity.ok(medicationReminderService.getAllActiveMedicationRemindersForOutput());

        return ResponseEntity.ok(medicationReminderService.getAllMedicationReminders());
    }

    @GetMapping("patient/{patientId}")
    public ResponseEntity<List<MedicationReminderOutputDTO>> getAllMedicationRemindersForPatient(@PathVariable String patientId) {
        log.info("Getting all medication reminders for patient {}", patientId);

        return ResponseEntity.ok(medicationReminderService.getActiveMedicationRemindersFilteredByPatientID(patientId));
    }

    @GetMapping("medication/{medicationId}")
    public ResponseEntity<List<MedicationReminderOutputDTO>> getAllMedicationRemindersForMedication(@PathVariable Integer medicationId) {
        log.info("Getting all medication reminders for medication {}", medicationId);

        return ResponseEntity.ok(medicationReminderService.getActiveMedicationRemindersFilteredByMedicationID(medicationId));
    }

    @GetMapping("patient/{patientId}/medication/{medicationId}")
    public ResponseEntity<List<MedicationReminderOutputDTO>> getAllMedicationRemindersForPatientAndMedication(@PathVariable String patientId, @PathVariable Integer medicationId) {
        log.info("Getting all medication reminders for patient {} & medication {}", patientId, medicationId);

        return ResponseEntity.ok(medicationReminderService.getActiveMedicationRemindersFilteredByPatientIDAndMedicationID(patientId, medicationId));
    }

    /*
     * 1. Create a reminder (Patient id, Medicine id, medicationTime, medicationDeadline)
     */

    @PostMapping
    public ResponseEntity<String> addMedicationReminder(@Valid @RequestBody MedicationReminderInputDTO medicationReminder) {
        log.info("Controller called for adding MedicationReminder at {}", LocalDateTime.now());
        medicationReminderService.saveReminderFromInput(medicationReminder);
        return ResponseEntity.ok("Successfully added");
    }

    /*
     * 1. Update patient's reminder list (Patient id, List.of(Medicine id, medicationTime, medicationDeadline))
     */

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMedicationReminder(@PathVariable(name = "id") Long id, @Valid @RequestBody MedicationReminderInputDTO medicationReminder) {
        log.info("Updating Medication Reminder at {}", LocalDateTime.now());

        medicationReminderService.updateReminder(id, medicationReminder);

        return ResponseEntity.ok("Reminder Successfully updated");
    }
}
