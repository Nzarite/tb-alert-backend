package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientMedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMedicationMapper;
import com.beehyv.tbalert.tbalertbackend.repository.PatientMedicationRepo;
import com.beehyv.tbalert.tbalertbackend.service.PatientMedicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PatientMedicationServiceImpl implements PatientMedicationService {

    private final PatientMedicationRepo patientMedicationRepo;
    private final PatientMedicationMapper patientMedicationMapper;
    private final PatientMapper patientMapper;

    @Override
    public List<PatientMedicationOutputDTO> get(int id) {
        log.info("Get patient medications by patient id: {}", id);
        List<PatientMedication> patientMedicationList=patientMedicationRepo.getPatientMedicationsByPatient(patientMapper.findPatient(id));
        return patientMedicationList.stream().map(patientMedicationMapper::toPatientMedicationOutputDTO).toList();
    }

    @Override
    public List<PatientMedicationOutputDTO> add(int id, List<PatientMedicationInputDTO> patientMedicationInputDTOList) {
        log.info("Add patient medications by patient id: {}", id);
        List<PatientMedicationOutputDTO> patientMedicationOutputDTOS=new ArrayList<>();
        patientMedicationInputDTOList.forEach(pm->{
            PatientMedication patientMedication=patientMedicationMapper.toPatientMedication(id, pm);
            patientMedicationRepo.save(patientMedication);
            patientMedicationOutputDTOS.add(patientMedicationMapper.toPatientMedicationOutputDTO(patientMedication));
        });
        return patientMedicationOutputDTOS;
    }
}
