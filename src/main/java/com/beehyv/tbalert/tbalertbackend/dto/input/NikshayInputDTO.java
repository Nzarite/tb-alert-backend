package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NikshayInputDTO {

    @NotEmpty(message = "Nikshay Id cannot be empty")
    private String nikshayId;

    @NotEmpty(message = "udst status cannot be empty")
    private Boolean udstStatus;

    private String dateOfUdst;

    private String resultOfUdst;

    private Boolean dbtStatus;

    private String dateOfDbt;

    private String nikshayMitraStatus;

    private String nikshayMitraDate;

    private String nikshayMitraName;

    @NotEmpty(message = "Patient ID cannot be empty")
    private Integer patientId;
}
