package com.beehyv.tbalert.tbalertbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicationDTO {
    private String medicationId;
    private String medicationName;
}
