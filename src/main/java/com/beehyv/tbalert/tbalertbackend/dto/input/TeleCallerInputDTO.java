package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TeleCallerInputDTO {

    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    private String lastName;

    @NotEmpty(message = "Enter valid phone number")
    private String phoneNumber;

    @Email(message = "Invalid Email")
    private String email;

    @NotEmpty(message = "Enter valid gender")
    private String gender;

    @NotEmpty(message = "Creator email is required")
    @Email(message = "Enter valid creator email")
    private String createdBy;

    private String block;

    private String gp;

    private String village;

    private String district;

    @NotEmpty(message = "State Name cannot be empty")
    private String state;

    @NotNull(message = "Enter Valid Date of Joining")
    private String dateOfJoining;

    private String dateOfLeaving;
}
