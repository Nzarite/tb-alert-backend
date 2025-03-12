package com.beehyv.tbalert.tbalertbackend.dto.output;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PatientFollowUpOutputDTO {

    private PatientOutputDTO patient;

    private List<MedicationDetails>medicationDetails;

    private String remarks;

    private String date;

    private String followUpStatus;

    private String createdBy;

    private String updatedBy;
}
