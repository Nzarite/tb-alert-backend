package com.beehyv.tbalert.tbalertbackend.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientFollowUpOutputForFrontEndDto {

    private PatientOutputDTO patient;

    List<FollowUpDetails>followUpDetails;

    @Data
    @Builder
    public static class FollowUpDetails{

        private List<MedicationDetails> medicationDetails;

        private String remarks;

        private String date;

        private String followUpStatus;

        private int patientCondition;
    }

}
