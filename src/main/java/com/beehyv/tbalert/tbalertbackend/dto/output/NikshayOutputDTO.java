package com.beehyv.tbalert.tbalertbackend.dto.output;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NikshayOutputDTO {

    private Integer id;
    private String nikshayId;
    private Boolean udstStatus;
    private String dateOfUdst;
    private String resultOfUdst;
    private Boolean dbtStatus;
    private String dateOfDbt;
    private String nikshayMitraStatus;
    private String nikshayMitraDate;
    private String nikshayMitraName;
    private Integer patientId;
}
