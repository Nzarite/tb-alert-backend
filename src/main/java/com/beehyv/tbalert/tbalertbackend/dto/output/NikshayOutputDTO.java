package com.beehyv.tbalert.tbalertbackend.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NikshayOutputDTO {
    private Integer id;
    private String nikshayId;
    private Boolean udstStatus;
    private String dateOfUdst;
    private String resultOfUdst;
    private Boolean dbtStatus;
    private String dateOfDbt;
    private Boolean nikshayMitraStatus;
    private String nikshayMitraDate;
    private String nikshayMitraName;
    private Integer patientId;
}
