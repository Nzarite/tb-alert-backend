package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class StateReportsInputDTO {

    @NotEmpty(message = "State name cannot be empty")
    private String state;

}
