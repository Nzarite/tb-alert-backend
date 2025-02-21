package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactScreeningInputDTO {
    @NotNull(message = "This field cannot be null")
    private Boolean contactScreeningDone;

    private String dateOfContactScreening;

    private Integer noOfHHCsAvailable;

    private Integer noOfHHCsScreened;

    private Integer noOfHHCsWithTBSymptoms;

    private Integer noOfHHCsReferredTBTesting;

    private Integer noOfHHCsDiagnosedTB;

    private Integer noOfHHCsTBInitiatedATT;

    private Integer noOfHHCsUndergoneLTBITest;

    private Integer noOfEligibleForTPT;

    private Integer noOfHHCsInitiatedTPT;

    @NotNull(message = "Patient Id cannot be empty")
    private String patientId;
}
