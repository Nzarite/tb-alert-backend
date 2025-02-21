package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PatientFollowUpInputDTO {

    @NotEmpty(message = "Empty date")
    private String date;

    private String remarks;

    private String aliveOrDead;

    private List<MissedMedicationInputDTO>missedMedications;

    private boolean cured;

    @NotNull
    private int patientCondition;

    private String followUpStatus;

}
