package com.beehyv.tbalert.tbalertbackend.dto.output;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AllPatientDetailsOutputDTO {

    private String patientId;

    private Long personId;

    private String firstName;

    private String lastName;

    private String gender;

    private String dateOfBirth;

    private String phoneNumber;

    private String email;

    private String mandal;

    private String gp;

    private String village;

    private String district;

    private String state;

    private String currentStatus;

    private boolean cured;

    private String createdAt;

    private String createdBy;

    private String updatedBy;

    private String nikshayId;

    private boolean udstStatus;

    private String dateOfUdst;

    private String resultOfUdst;

    private boolean dbtStatus;

    private String dateOfDbt;

    private String nikshayMitraStatus;

    private String nikshayMitraDate;

    private String nikshayMitraName;

    private boolean contactScreeningDone;

    private String dateOfContactScreening;

    private Integer noOfHHCsAvailable;

    private Integer noOfHHCsScreened;

    private Integer noOfHHCsWithTBSymptoms;

    private Integer noOfHHCsReferredTBTesting;

    private Integer noOfHHCsDiagnosedTB;

    private Integer noOfHHCsTBInitiatedATT;

    private Integer noOfHHCsUndergoneLTBITest;

    private Integer noOfEligibleForTPT;

    private Integer noOfHHCsInitiatedTPT;

    private String dateOfDiagnosis;

    private String dateOfTreatmentInitiation;

    private String typeOfPwtb;

    private String typeOfTb;

    private String dstbOrDrtb;

}
