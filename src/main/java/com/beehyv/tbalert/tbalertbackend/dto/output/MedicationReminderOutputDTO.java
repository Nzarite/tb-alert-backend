package com.beehyv.tbalert.tbalertbackend.dto.output;

import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class MedicationReminderOutputDTO {

    private PatientOutputDTO patient;

    private String medicationName;

    private String medicationTime;

    private String deadline;
}
