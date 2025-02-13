package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientInputDTO {

    @NotEmpty(message = "Person Id cannot be empty")
    int personId;

    String currentStatus;
}
