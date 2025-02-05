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

    @NotEmpty(message = "Block name cannot be empty")
    private String block;

    @NotEmpty(message = "Gram Panchayat name cannot be empty")
    private String gp;

    @NotEmpty(message = "Village name cannot be empty")
    private String village;

    @NotEmpty(message = "District name cannot be empty")
    private String district;
}
