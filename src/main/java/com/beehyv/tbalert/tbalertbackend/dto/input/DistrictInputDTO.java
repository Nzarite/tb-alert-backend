package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DistrictInputDTO {

    @NotNull(message = "Invalid state id")
    private String stateName;

    @NotEmpty(message = "Enter district name")
    private String districtName;
}
