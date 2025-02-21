package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientMedicationInputDTO {

    @NotEmpty(message = "medication id cannot be empty")
    private int medicationId;

    @NotEmpty(message = "enter valid frequency")
    private int frequency;

}
