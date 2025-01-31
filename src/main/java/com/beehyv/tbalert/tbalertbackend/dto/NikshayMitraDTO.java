package com.beehyv.tbalert.tbalertbackend.dto;

import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NikshayMitraDTO {
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
