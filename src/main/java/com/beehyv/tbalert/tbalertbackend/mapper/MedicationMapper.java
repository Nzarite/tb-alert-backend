package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.MedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Medication;
import com.beehyv.tbalert.tbalertbackend.repository.MedicationRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MedicationMapper {

    private final MedicationRepo medicationRepo;

    public Medication findMedicationById(int medicationId) {
       log.info("Mappper called for Find medication by id: {}", medicationId);
       return medicationRepo.findById(medicationId).orElseThrow(()-> new IllegalArgumentException("Medication not found for Id: " + medicationId));
    }

    public Medication toMedication (MedicationInputDTO medicationInputDTO) {
        log.info("Mappper called for to medication by input: {}", medicationInputDTO);
        return Medication.builder()
                .name(medicationInputDTO.getName())
                .beforeMeal(medicationInputDTO.isBeforeMeal())
                .build();
    }

    public MedicationOutputDTO toMedicationOutputDTO(Medication medication)
    {
        log.info("Mappper called for to medicationOutputDto by input: {}", medication);
        return MedicationOutputDTO.builder()
                .id(medication.getId())
                .name(medication.getName())
                .beforeMeal(medication.isBeforeMeal())
                .build();
    }

    public String nameToId (String name) {
        log.info("Mappper called for mapNameToId by input: {}", name);
        try{
            return String.valueOf(medicationRepo.findByName(name).getId());
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Medication not found for name: " + name);
        }
    }

    public String idToName(int id) {
        log.info("Mappper called for mapIdToName by input: {}", id);
        try{
            return String.valueOf(medicationRepo.findById(id).get().getName());
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Medication not found for id: " + id);
        }
    }
}
