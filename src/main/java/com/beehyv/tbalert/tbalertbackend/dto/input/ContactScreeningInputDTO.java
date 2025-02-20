package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ContactScreeningInputDTO {
    @NotNull(message = "This field cannot be null")
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
}
