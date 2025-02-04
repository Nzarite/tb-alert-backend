package com.beehyv.tbalert.tbalertbackend.dto.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicationDetails {

    private int medicationId;

    private String medicationName;

    private int missedDosages;

    private String comments;

}
