package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientFollowUpInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputForFrontEndDto.FollowUpDetails;
import com.beehyv.tbalert.tbalertbackend.entity.MissedMedication;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.PatientFollowUp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PatientFollowUpMapper {

    private final PatientMapper patientMapper;
    private final MedicationDetailsMapper medicationDetailsMapper;
    private final LocalDateMapper localDateMapper;


    public PatientFollowUpOutputDTO toDTO(PatientFollowUp patientFollowUp, List<MissedMedication> missedMedicationList) {
        return  PatientFollowUpOutputDTO.builder()
                .patient(patientMapper.toPatientOutputDTO(patientFollowUp.getPatient()))
                .medicationDetails(missedMedicationList.stream().map(medicationDetailsMapper::toMedicationDetails).toList())
                .remarks(patientFollowUp.getRemarks())
                .date(patientFollowUp.getDate().toString())
                .followUpStatus(patientFollowUp.getOccured() != null && patientFollowUp.getOccured())
                .build();
    }
    public PatientFollowUp toPatientFollowUp(PatientFollowUpInputDTO patientFollowUpInputDTO, Patient patient) {
        return PatientFollowUp.builder()
                .patient(patient)
                .date(localDateMapper.toLocalDate(patientFollowUpInputDTO.getDate()))
                .remarks(patientFollowUpInputDTO.getRemarks())
                .occured(false)
                .patientCondition(patientFollowUpInputDTO.getPatientCondition())
                .build();
    }


    public FollowUpDetails toFollowUpDetails(PatientFollowUp patientFollowUp,List<MissedMedication>missedMedicationList) {
        return   FollowUpDetails.builder()
                .followUpStatus(patientFollowUp.getOccured() != null && patientFollowUp.getOccured())
                .medicationDetails(missedMedicationList.stream().map(medicationDetailsMapper::toMedicationDetails).toList())
                .remarks(patientFollowUp.getRemarks())
                .date(patientFollowUp.getDate().toString())
                .patientCondition(patientFollowUp.getPatientCondition())
                .build();
    }
}
