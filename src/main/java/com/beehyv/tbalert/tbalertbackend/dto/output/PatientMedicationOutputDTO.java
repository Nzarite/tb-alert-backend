package com.beehyv.tbalert.tbalertbackend.dto.output;

import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientMedicationOutputDTO {

    private Patient patient;

    private String medication;

    private int medicationId;

    private int frequency;
}
