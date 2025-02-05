package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientFollowUpInputDTO {

    @NotEmpty(message = "Empty date")
    private String date;

    private String remarks;
}
