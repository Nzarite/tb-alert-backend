package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MissedMedicationInputDTO {

    @NotNull
    private int medicationId;

    private int missedDoses;

    private String comment;
}
