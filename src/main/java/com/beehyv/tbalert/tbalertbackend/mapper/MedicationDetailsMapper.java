package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.output.MedicationDetails;
import com.beehyv.tbalert.tbalertbackend.entity.MissedMedication;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MedicationDetailsMapper {

    public MedicationDetails toMedicationDetails(MissedMedication missedMedication) {
        PatientMedication patientMedication = missedMedication.getPatientMedication();
        log.info("PatientMedication mapped called: {}", patientMedication);
        return MedicationDetails.builder()
                .medicationId(missedMedication.getPatientMedication().getMedication().getId())
                .medicationName(missedMedication.getPatientMedication().getMedication().getName())
                .missedDosages(missedMedication.getMissedDosages())
                .comments(missedMedication.getComment())
                .build();
    }
}
