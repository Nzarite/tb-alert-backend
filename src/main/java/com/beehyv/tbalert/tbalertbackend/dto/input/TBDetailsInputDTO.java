package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TBDetailsInputDTO {
    @NotEmpty
    private String dateOfDiagnosis;
    @NotEmpty
    private String dateOfTreatmentInitiation;
    @NotEmpty
    private String typeOfPwtb;
    @NotEmpty
    private String typeOfTb;
    @NotEmpty
    private String dstbOrDrtb;
    @NotEmpty
    private String clinicalOrMicrobiological;

    private String updatedBy;

    private String patientId;
}
