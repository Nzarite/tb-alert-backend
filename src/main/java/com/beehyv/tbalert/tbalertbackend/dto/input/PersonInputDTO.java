package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PersonInputDTO {

    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    private String lastName;

    @NotEmpty(message = "Enter valid phone number")
    private String phoneNumber;

    @Email(message = "Invalid Email")
    private String email;

    @NotEmpty(message = "Enter valid gender")
    private String gender;

    @NotEmpty(message = "Enter valid Date of Birth")
    private String dateOfBirth;

    @Email(message = "Enter valid creator email")
    private String createdBy;

    @NotEmpty(message = "Block name cannot be empty")
    private String block;

    @NotEmpty(message = "Gram Panchayat name cannot be empty")
    private String gp;

    @NotEmpty(message = "Village name cannot be empty")
    private String village;

    @NotEmpty(message = "District name cannot be empty")
    private String district;

    @NotEmpty(message = "State name cannot be empty")
    private String state;

}
