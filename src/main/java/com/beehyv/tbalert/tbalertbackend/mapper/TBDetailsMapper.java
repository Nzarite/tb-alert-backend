package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.TBDetailsInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.TBDetailsOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.TBDetails;
import com.beehyv.tbalert.tbalertbackend.repository.NikshayMitraRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class TBDetailsMapper {

    private final PatientRepo patientRepo;
    private final NikshayMitraRepo nikshayMitraRepo;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public TBDetails toTBDetails(TBDetailsInputDTO tbDetailsInputDTO) {
        Patient patient = patientRepo.findById(tbDetailsInputDTO.getPatientId()).orElseThrow(() -> new IllegalArgumentException("Invalid Patient ID: " + tbDetailsInputDTO.getPatientId()));

        return TBDetails.builder()
                .dateOfDiagnosis(LocalDate.parse(tbDetailsInputDTO.getDateOfDiagnosis(), formatter))
                .dateOfTreatmentInitiation(LocalDate.parse(tbDetailsInputDTO.getDateOfTreatmentInitiation(), formatter))
                .typeOfPwtb(tbDetailsInputDTO.getTypeOfPwtb())
                .typeOfTb(tbDetailsInputDTO.getTypeOfTb())
                .dstbOrDrtb(tbDetailsInputDTO.getDstbOrDrtb())
                .patient(patient)
                .build();
    }

    public TBDetailsOutputDTO toOutputDto(TBDetails tbDetails) {
        return TBDetailsOutputDTO.builder()
                .id(tbDetails.getId())
                .dateOfDiagnosis(tbDetails.getDateOfDiagnosis().toString())
                .dateOfTreatmentInitiation(tbDetails.getDateOfTreatmentInitiation().toString())
                .typeOfPwtb(tbDetails.getTypeOfPwtb())
                .typeOfTb(tbDetails.getTypeOfTb())
                .dstbOrDrtb(tbDetails.getDstbOrDrtb())
                .patientId(tbDetails.getPatient().getId())
                .build();
    }
}
