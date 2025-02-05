package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientInputDTO {

    @NotEmpty(message = "Firstname cannot be empty")
    private String firstName;

    private String lastName;

    @NotEmpty(message = "Gender cannot be empty")
    private String gender;

    @NotEmpty(message = "Phone number cannot be empty")
    private String phone;

    @NotNull
    private String dateOfBirth;

    private String block;

    private String gp;

    private String village;

    private String district;
}
