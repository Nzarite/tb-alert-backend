package com.beehyv.tbalert.tbalertbackend.dto.output;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TBDetailsOutputDTO {
    private Integer id;
    private String dateOfDiagnosis;
    private String dateOfTreatmentInitiation;
    private String typeOfPwtb;
    private String typeOfTb;
    private String dstbOrDrtb;
    private Integer patientId;
}
