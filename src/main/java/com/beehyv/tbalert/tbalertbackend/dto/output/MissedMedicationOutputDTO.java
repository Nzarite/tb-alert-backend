package com.beehyv.tbalert.tbalertbackend.dto.output;

import com.beehyv.tbalert.tbalertbackend.entity.Medication;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MissedMedicationOutputDTO {

    private Patient patient;

    private Medication medication;

    private int missedDoses;

    private String comment;

    private LocalDate date;
}
