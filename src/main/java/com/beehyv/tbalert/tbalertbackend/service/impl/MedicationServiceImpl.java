package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.MedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Medication;
import com.beehyv.tbalert.tbalertbackend.mapper.MedicationMapper;
import com.beehyv.tbalert.tbalertbackend.repository.MedicationRepo;
import com.beehyv.tbalert.tbalertbackend.service.MedicationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepo medicationRepo;
    private final MedicationMapper medicationMapper;

    @Override
    public MedicationOutputDTO add(MedicationInputDTO medication) {
        Medication m = medicationMapper.toMedication(medication);
        medicationRepo.save(m);
        return medicationMapper.toMedicationOutputDTO(m);
    }

    @Override
    public List<MedicationOutputDTO> getAll() {
        return medicationRepo.findAll().stream().map(m->medicationMapper.toMedicationOutputDTO(m)).collect(Collectors.toList());
    }
}
