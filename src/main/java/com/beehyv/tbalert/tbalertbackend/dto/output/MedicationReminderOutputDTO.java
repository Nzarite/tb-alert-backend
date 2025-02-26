package com.beehyv.tbalert.tbalertbackend.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MedicationReminderOutputDTO {

    private PatientOutputDTO patient;

    private String medicationName;

    private String medicationTime;

    private String deadline;
}
