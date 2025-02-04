package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PatientFollowUpInputDTO {

    @NotEmpty(message = "Empty date")
    private String date;

    private String remarks;
}
