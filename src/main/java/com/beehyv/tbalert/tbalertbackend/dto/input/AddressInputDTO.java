package com.beehyv.tbalert.tbalertbackend.dto.input;

import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressInputDTO {
    private Integer id;

    private String block;

    private String gp;

    private String village;

    private String district;

    private Patient patient;
}
