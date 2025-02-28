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
    private String clinicalOrMicrobiological;
    private String patientId;

    @Data
    @Builder
    public static class MedicationDetails {

        private int medicationId;

        private String medicationName;

        private int missedDosages;

        private String comments;

    }
}
