package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientInputDTO {

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

    @NotNull(message = "Enter valid age")
    private int age;

    @NotNull(message = "Enter valid Gram Panchayat Id")
    private Long gramPanchayatId;

    @NotEmpty(message = "Enter village name")
    private String village;

    @NotNull(message = "Provide the patient consent for message reminders")
    private Boolean consentForMessage;

    private String reminderTime;

    private Boolean isDiagnosedWithTB;
}
