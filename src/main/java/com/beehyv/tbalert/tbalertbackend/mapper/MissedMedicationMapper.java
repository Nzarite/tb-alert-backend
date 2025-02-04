package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.MissedMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MissedMedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Medication;
import com.beehyv.tbalert.tbalertbackend.entity.MissedMedication;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
import com.beehyv.tbalert.tbalertbackend.repository.MissedMedicationRepo;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class MissedMedicationMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final MissedMedicationRepo missedMedicationRepo;

    public MissedMedication toMissedMedication(MissedMedicationInputDTO missedMedicationInputDTO, PatientMedication patientMedication) {
        return MissedMedication.builder()
                .patientMedication(patientMedication)
                .missedDosages(missedMedicationInputDTO.getMissedDoses())
                .comment(missedMedicationInputDTO.getComment())
                .date(LocalDate.parse(missedMedicationInputDTO.getDate(), formatter))
                .build();
    }

    public MissedMedicationOutputDTO toMissedMedicationOutputDTO(MissedMedication missedMedication) {
        return MissedMedicationOutputDTO.builder()
                .medication(missedMedication.getPatientMedication().getMedication())
                .patient(missedMedication.getPatientMedication().getPatient())
                .missedDoses(missedMedication.getMissedDosages())
                .comment(missedMedication.getComment())
                .date(missedMedication.getDate())
                .build();
    }

//    public Medication find(@NotEmpty int medicationId) {
//
//        MissedMedication missedMedication = missedMedicationRepo.findById(medicationId).orElseThrow(()->new IllegalArgumentException("Missed medication not found"));
//        return missedMedication;
//    }
}
