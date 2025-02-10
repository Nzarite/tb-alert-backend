package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PatientReportInput {
    @NotEmpty
    String key;

    Object value;
}
