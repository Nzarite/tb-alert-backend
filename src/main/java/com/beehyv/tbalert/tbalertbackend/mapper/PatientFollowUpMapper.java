package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientFollowUpInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.MissedMedication;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.PatientFollowUp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PatientFollowUpMapper {

    private final PatientMapper patientMapper;
    private final MedicationDetailsMapper medicationDetailsMapper;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public PatientFollowUpOutputDTO toDTO(PatientFollowUp patientFollowUp, List<MissedMedication> missedMedicationList) {
        return  PatientFollowUpOutputDTO.builder()
                .patient(patientMapper.toPatientOutputDTO(patientFollowUp.getPatient()))
                .medicationDetails(missedMedicationList.stream().map(medicationDetailsMapper::toMedicationDetails).collect(Collectors.toList()))
                .remarks(patientFollowUp.getRemarks())
                .date(patientFollowUp.getDate().toString())
                .followUpStatus(patientFollowUp.getOccured() != null && patientFollowUp.getOccured())
                .build();
    }

    public PatientFollowUp toPatientFollowUp(PatientFollowUpInputDTO patientFollowUpInputDTO, Patient patient, List<MissedMedication> missedMedicationList) {
        return PatientFollowUp.builder()
                .patient(patient)
                .missedMedicationList(missedMedicationList)
                .date(LocalDate.parse(patientFollowUpInputDTO.getDate(),formatter))
                .remarks(patientFollowUpInputDTO.getRemarks())
                .occured(true)
                .build();
    }
}
