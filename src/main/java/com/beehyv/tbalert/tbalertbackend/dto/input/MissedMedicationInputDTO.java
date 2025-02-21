package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MissedMedicationInputDTO {

    @NotNull
    private int medicationId;

    private int missedDosages;

    private String comments;
}
