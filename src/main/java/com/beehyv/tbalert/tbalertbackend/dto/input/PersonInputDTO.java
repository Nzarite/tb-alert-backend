package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PersonInputDTO {

    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    private String lastName;

    @NotEmpty(message = "Enter valid State")
    private String State;

    @NotEmpty(message = "Enter valid Role")
    private String Role;

    @NotEmpty(message = "Enter valid phone number")
    private String phoneNumber;

    @Email(message = "Invalid Email")
    private String email;

    private String gender;

}
