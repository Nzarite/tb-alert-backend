package com.beehyv.tbalert.tbalertbackend.dto.output;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonOutputDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String State;

    private String Role;

    private String phoneNumber;

    private String email;

    private String gender;
}
