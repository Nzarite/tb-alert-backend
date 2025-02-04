package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.model.MedicationDetails;
import com.beehyv.tbalert.tbalertbackend.entity.Medication;
import com.beehyv.tbalert.tbalertbackend.entity.MissedMedication;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MedicationDetailsMapper {

    public MedicationDetails toMedicationDetails(MissedMedication missedMedication) {
        PatientMedication patientMedication = missedMedication.getPatientMedication();
        return MedicationDetails.builder()
                .medicationId(missedMedication.getPatientMedication().getMedication().getId())
                .medicationName(missedMedication.getPatientMedication().getMedication().getName())
                .missedDosages(missedMedication.getMissedDosages())
                .comments(missedMedication.getComment())
                .build();
    }
}
