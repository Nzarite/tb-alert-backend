package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.MedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Medication;
import com.beehyv.tbalert.tbalertbackend.mapper.MedicationMapper;
import com.beehyv.tbalert.tbalertbackend.repository.MedicationRepo;
import com.beehyv.tbalert.tbalertbackend.service.MedicationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepo medicationRepo;
    private final MedicationMapper medicationMapper;

    @Override
    public MedicationOutputDTO add(MedicationInputDTO medication) {
        log.info("Service called for Adding medication: {}", medication);
        Medication m = medicationMapper.toMedication(medication);
        medicationRepo.save(m);
        return medicationMapper.toMedicationOutputDTO(m);
    }

    @Override
    public List<MedicationOutputDTO> getAll() {
        log.info("Service called for Getting all medications");
        return medicationRepo.findAll().stream().map(medicationMapper::toMedicationOutputDTO).collect(Collectors.toList());
    }
}
