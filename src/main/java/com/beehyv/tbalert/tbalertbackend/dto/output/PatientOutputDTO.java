package com.beehyv.tbalert.tbalertbackend.dto.output;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientOutputDTO {

    private String patientId;

    private Long personId;

    private String firstName;

    private String lastName;

    private String gender;

    private int age;

    private String phoneNumber;

    private String email;

    private String block;

    private String gp;

    private String village;

    private String district;

    private String state;

    private String currentStatus;

    private boolean cured;

    private String createdAt;

    private String createdBy;

    private String updatedBy;

    private Boolean consentForMessage;

}
