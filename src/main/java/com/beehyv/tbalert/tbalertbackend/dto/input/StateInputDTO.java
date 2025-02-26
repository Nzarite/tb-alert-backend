package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class StateInputDTO {

    @NotEmpty(message = "State name cannot be empty")
    private String stateName;

    @NotEmpty(message = "State code cannot be empty")
    private String stateCode;
}
