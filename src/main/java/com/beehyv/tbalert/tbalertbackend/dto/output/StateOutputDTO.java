package com.beehyv.tbalert.tbalertbackend.dto.output;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StateOutputDTO {
    private Long id;

    @NotEmpty(message = "State name cannot be empty")
    private String stateName;

    @NotEmpty(message = "State code cannot be empty")
    private String stateCode;
}
