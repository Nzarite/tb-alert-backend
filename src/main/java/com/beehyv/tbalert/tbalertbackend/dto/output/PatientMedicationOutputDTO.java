package com.beehyv.tbalert.tbalertbackend.dto.output;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientMedicationOutputDTO {

    private String medicationName;

    private int medicationId;

    private int frequency;
}
