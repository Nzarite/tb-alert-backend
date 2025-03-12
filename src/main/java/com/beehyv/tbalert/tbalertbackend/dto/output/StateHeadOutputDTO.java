package com.beehyv.tbalert.tbalertbackend.dto.output;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StateHeadOutputDTO {

    private Long stateHeadId;

    private Long personId;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String gender;

    private String mandal;

    private String gp;

    private String village;

    private String district;

    private String state;

    private String createdBy;

    private String createdOn;

    private String updatedBy;

    private String dateOfJoining;

    private String dateOfLeaving;

}
