package com.beehyv.tbalert.tbalertbackend.dto.output;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ContactScreeningOutputDTO {
    private Integer id;
    private Boolean contactScreeningDone;
    private LocalDate dateOfContactScreening;
    private Integer noOfHHCsAvailable;
    private Integer noOfHHCsScreened;
    private Integer noOfHHCsWithTBSymptoms;
    private Integer noOfHHCsReferredTBTesting;
    private Integer noOfHHCsDiagnosedTB;
    private Integer noOfHHCsTBInitiatedATT;
    private Integer noOfHHCsUndergoneLTBITest;
    private Integer noOfEligibleForTPT;
    private Integer noOfHHCsInitiatedTPT;
    private Integer patientId;
}
