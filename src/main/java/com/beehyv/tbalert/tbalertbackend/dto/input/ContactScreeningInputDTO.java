package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ContactScreeningInputDTO {
    @NotEmpty(message = "Id cannot be empty")
    private Integer id;
    @NotEmpty(message = "This field cannot be empty")
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
    @NotEmpty(message = "Patient Id cannot be empty")
    private Integer patientId;
}
