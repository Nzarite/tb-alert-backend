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

    private int age;

    private String village;

    private Long gramPachayatId;

    private String currentStatus;

    @Email(message = "Provide email of authorising body for update")
    private String updatedBy;

    private Boolean consentForMessage;

    private Boolean isDiagnosedWithTB;

    private String reminderTime;
}
