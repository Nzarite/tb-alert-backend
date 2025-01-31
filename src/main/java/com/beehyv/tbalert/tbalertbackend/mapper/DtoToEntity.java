package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.NikshayMitraDTO;
import com.beehyv.tbalert.tbalertbackend.entity.NikshayMitra;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.repository.PatientRepo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Builder
public class DtoToEntity {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final PatientRepo patientRepo;

    public NikshayMitra nikshayDtoToEntity(NikshayMitraDTO nikshayMitraDto) {

        Patient patient = patientRepo.findById(nikshayMitraDto.getPatientId()).orElseThrow(() -> new IllegalArgumentException("Invalid Patient ID: " + nikshayMitraDto.getPatientId()));

        return NikshayMitra.builder()
                .nikshayId(nikshayMitraDto.getNikshayId())
                .udstStatus(nikshayMitraDto.getUdstStatus())
                .dateOfUdst(LocalDate.parse(nikshayMitraDto.getDateOfUdst(),formatter))
                .resultOfUdst(nikshayMitraDto.getResultOfUdst())
                .dbtStatus(nikshayMitraDto.getDbtStatus())
                .dateOfDbt(LocalDate.parse(nikshayMitraDto.getDateOfDbt(),formatter))
                .nikshayMitraStatus(nikshayMitraDto.getNikshayMitraStatus())
                .nikshayMitraDate(LocalDate.parse(nikshayMitraDto.getNikshayMitraDate(),formatter))
                .patient(patient)
                .build();
    }
}
