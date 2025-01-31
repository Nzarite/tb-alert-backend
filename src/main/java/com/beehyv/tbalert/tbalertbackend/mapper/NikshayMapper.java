package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.NikshayInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.NikshayOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.NikshayMitra;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.repository.PatientRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class NikshayMapper {

    private final PatientRepo patientRepo;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public NikshayMitra ToEntity(NikshayInputDTO nikshayInputDTO) {

        Patient patient = patientRepo.findById(nikshayInputDTO.getPatientId()).orElseThrow(() -> new IllegalArgumentException("Invalid Patient ID: " + nikshayInputDTO.getPatientId()));

        return NikshayMitra.builder()
                .nikshayId(nikshayInputDTO.getNikshayId())
                .udstStatus(nikshayInputDTO.getUdstStatus())
                .dateOfUdst(LocalDate.parse(nikshayInputDTO.getDateOfUdst(), formatter))
                .resultOfUdst(nikshayInputDTO.getResultOfUdst())
                .dbtStatus(nikshayInputDTO.getDbtStatus())
                .dateOfDbt(LocalDate.parse(nikshayInputDTO.getDateOfDbt(), formatter))
                .nikshayMitraStatus(nikshayInputDTO.getNikshayMitraStatus())
                .nikshayMitraDate(LocalDate.parse(nikshayInputDTO.getNikshayMitraDate(), formatter))
                .patient(patient)
                .build();
    }

    public NikshayOutputDTO ToOutputDto(NikshayMitra nikshayMitra) {
        return NikshayOutputDTO.builder()
                .id(nikshayMitra.getId())
                .nikshayId(nikshayMitra.getNikshayId())
                .udstStatus(nikshayMitra.getUdstStatus())
                .dateOfUdst(nikshayMitra.getDateOfUdst().toString())
                .resultOfUdst(nikshayMitra.getResultOfUdst())
                .dbtStatus(nikshayMitra.getDbtStatus())
                .dateOfDbt(nikshayMitra.getDateOfDbt().toString())
                .nikshayMitraStatus(nikshayMitra.getNikshayMitraStatus())
                .nikshayMitraDate(nikshayMitra.getNikshayMitraDate().toString())
                .nikshayMitraName(nikshayMitra.getNikshayMitraName())
                .patientId(nikshayMitra.getPatient().getId())
                .build();
    }
}
