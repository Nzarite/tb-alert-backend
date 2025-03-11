package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MandalInputDTO {

    @NotNull(message = "Invalid District Id")
    private Long districtId;

    @NotEmpty(message = "Invalid Mandal name")
    private String mandalName;

}
