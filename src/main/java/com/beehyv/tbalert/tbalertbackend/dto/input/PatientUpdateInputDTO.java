package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class PatientUpdateInputDTO {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String gender;

    private String dateOfBirth;

    private String block;

    private String gp;

    private String village;

    private String district;

    private String state;

    private String currentStatus;

    @Email(message = "Provide email of authorising body for update")
    private String updatedBy;
}
