package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MedicationReminderInputDTO {

    @NotEmpty(message = "Patient ID is required")
    private Integer patientId;

    @NotEmpty(message = "Medication ID is required")
    private Integer medicationId;

    @NotEmpty(message = "Medication time is required")
    @Pattern(regexp = "^([0-1]?\\d|2[0-3]):[0-5]\\d$", message = "Invalid Time Format. The correct Format is HH:mm")
    private String medicationTime;

    @NotEmpty(message = "Medication Deadline is required")
    @FutureOrPresent(message = "Deadline should be in the future")
    private String medicationDeadline;
}
