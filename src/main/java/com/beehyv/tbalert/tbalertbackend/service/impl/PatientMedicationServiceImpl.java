package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientMedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMedicationMapper;
import com.beehyv.tbalert.tbalertbackend.repository.MissedMedicationRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientMedicationRepo;
import com.beehyv.tbalert.tbalertbackend.service.PatientMedicationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class PatientMedicationServiceImpl implements PatientMedicationService {

    private final PatientMedicationRepo patientMedicationRepo;
    private final PatientMedicationMapper patientMedicationMapper;
    private final PatientMapper patientMapper;
    private final MissedMedicationRepo missedMedicationRepo;

    @Override
    public List<PatientMedicationOutputDTO> get(String id) {
        log.info("Get patient medications by patient id: {}", id);

        List<PatientMedication> patientMedicationList=patientMedicationRepo.getPatientMedicationsByPatient(patientMapper.find(id));
        return patientMedicationList.stream().map(patientMedicationMapper::toPatientMedicationOutputDTO).toList();
    }

    @Override
    public List<PatientMedicationOutputDTO> add(String id, List<PatientMedicationInputDTO> patientMedicationInputDTOList) {
        log.info("Add patient medications by patient id: {}", id);
        List<PatientMedication> patientMedications;
        List<Integer> medications = new ArrayList<>();
        List<Integer> frequency = new ArrayList<>();
        patientMedicationInputDTOList.forEach(pm -> {
            medications.add(pm.getMedicationId());
            frequency.add(pm.getFrequency());
        });
        patientMedications = patientMedicationMapper.toPatientMedications(id, medications, frequency);
        List<PatientMedicationOutputDTO> patientMedicationOutputDTOS = new ArrayList<>(patientMedications.stream().map(patientMedicationMapper::toPatientMedicationOutputDTO).toList());
        patientMedicationRepo.saveAll(patientMedications);
        return patientMedicationOutputDTOS;
    }

    @Override
    public void delete(String id) {
        log.info("Delete patient medications by patient id: {}", id);

        List<PatientMedication>patientMedications=patientMedicationRepo.findAllByPatient_Id(id);
        missedMedicationRepo.deleteAllByPatientMedicationIn(patientMedications);
        patientMedicationRepo.deleteAll(patientMedications);
        log.info("Deleted patient medications by patient id: {}", id);
    }
}
