package com.beehyv.tbalert.tbalertbackend.dto.output;

import com.beehyv.tbalert.tbalertbackend.dto.model.MedicationDetails;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PatientFollowUpOutputDTO {

    private PatientOutputDTO patient;

    private List<MedicationDetails>medicationDetails;

    private String remarks;

    private String date;

    private Boolean followUpStatus;
}
