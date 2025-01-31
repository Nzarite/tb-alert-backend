package com.beehyv.tbalert.tbalertbackend.dto.output;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientOutputDTO {

    private String firstName;

    private String lastName;

    private String gender;

    private String phone;

    private String block;

    private String gp;

    private String village;

    private String district;
}
