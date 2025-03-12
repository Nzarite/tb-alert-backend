package com.beehyv.tbalert.tbalertbackend.dto.output;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonOutputDTO {

    private Long id;

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
}
