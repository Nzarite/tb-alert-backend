package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.MedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Medication;
import com.beehyv.tbalert.tbalertbackend.repository.MedicationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MedicationMapper {

    private final MedicationRepo medicationRepo;

    public Medication findMedicationById(int medicationId) {
        Medication medication = medicationRepo.findById(medicationId).orElseThrow(()-> new IllegalArgumentException("Medication not found"));
        return medication;
    }

    public Medication toMedication (MedicationInputDTO medicationInputDTO) {
        return Medication.builder()
                .name(medicationInputDTO.getName())
                .beforeMeal(medicationInputDTO.isBeforeMeal())
                .build();
    }

    public MedicationOutputDTO toMedicationOutputDTO(Medication medication)
    {
        return MedicationOutputDTO.builder()
                .id(medication.getId())
                .name(medication.getName())
                .beforeMeal(medication.isBeforeMeal())
                .build();
    }
}
