package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.MissedMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MissedMedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.*;
import com.beehyv.tbalert.tbalertbackend.mapper.MedicationMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.MissedMedicationMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMapper;
import com.beehyv.tbalert.tbalertbackend.repository.MedicationRepo;
import com.beehyv.tbalert.tbalertbackend.repository.MissedMedicationRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientFollowUpRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientMedicationRepo;
import com.beehyv.tbalert.tbalertbackend.service.MissedMedicationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class MissedMedicationImpl implements MissedMedicationService {

    private final PatientMapper patientMapper;
    private final MissedMedicationMapper missedMedicationMapper;
    private final MissedMedicationRepo missedMedicationRepo;
    private final PatientMedicationRepo patientMedicationRepo;
    private final PatientFollowUpRepo patientFollowUpRepo;

    private final MedicationRepo medicationRepo;


    @Override
    public List<MissedMedicationOutputDTO> add(String id, @Valid List<MissedMedicationInputDTO> missedMedicationInputDTOS,LocalDate date) {
        log.info("Service called for adding missed medication: {}", missedMedicationInputDTOS);
        Patient patient=patientMapper.findPatient(id);

        List<Medication>medications=medicationRepo.findAllByIdIn(missedMedicationInputDTOS.stream().map(MissedMedicationInputDTO::getMedicationId).toList());

        Map<Integer, PatientMedication> patientMedicationMap = patientMedicationRepo.findByPatientAndMedicationIn(patient, medications)
                .stream().collect(Collectors.toMap(pm -> pm.getMedication().getId(), pm -> pm));

        List<MissedMedication> existingMissedMedications = missedMedicationRepo.findByPatientMedicationInAndDate(new ArrayList<>(patientMedicationMap.values()), date);
        Map<Integer, MissedMedication> missedMedicationMap = existingMissedMedications.stream()
                .collect(Collectors.toMap(mm -> mm.getPatientMedication().getMedication().getId(), mm -> mm));

        List<MissedMedication> toSaveMissedMedications = new ArrayList<>();
        List<MissedMedicationOutputDTO> missedMedicationOutputDTOS = new ArrayList<>();
        List<PatientFollowUp>followUpsToSave = new ArrayList<>();
        log.info("Size of input = {}", missedMedicationInputDTOS.size());
        for (MissedMedicationInputDTO inputDTO : missedMedicationInputDTOS) {
            int medicationId = inputDTO.getMedicationId();
            PatientMedication patientMedication = patientMedicationMap.get(medicationId);
            if (patientMedication == null) {
                throw new IllegalArgumentException("Patient Medication not found for patient " + patient.getId() + " medication " + medicationId);
            }

            MissedMedication missedMedication;
            if (!missedMedicationMap.containsKey(medicationId)) {
                missedMedication = missedMedicationMapper.toMissedMedication(inputDTO, patientMedication, date);
            } else {
                missedMedication = missedMedicationMap.get(medicationId);
                PatientFollowUp patientFollowUp = patientFollowUpRepo.findByPatientAndDate(patient,missedMedication.getDate());
                patientFollowUp.setStatus("Occured");
                followUpsToSave.add(patientFollowUp);

                log.info("Missed Dosages : {}", inputDTO.getMissedDosages());
                missedMedication.setComment(inputDTO.getComments());
                missedMedication.setMissedDosages(inputDTO.getMissedDosages());
            }
            toSaveMissedMedications.add(missedMedication);
            missedMedicationOutputDTOS.add(missedMedicationMapper.toMissedMedicationOutputDTO(missedMedication));
        }
            missedMedicationRepo.saveAll(toSaveMissedMedications);
            patientFollowUpRepo.saveAll(followUpsToSave);
            return missedMedicationOutputDTOS;

    }

    @Override
    public List<MissedMedicationOutputDTO> get(String id) {
        log.info("Service called for getByPersonId missed medication: {}", id);
        List<PatientMedication> patientMedications=patientMedicationRepo.findPatientMedicationByPatient(patientMapper.findPatient(id));
        List<MissedMedicationOutputDTO>missedMedicationOutputDTOS=new ArrayList<>();
        patientMedications.forEach(patientMedication -> missedMedicationOutputDTOS.addAll(missedMedicationRepo.findAllByPatientMedication(patientMedication).stream().map(missedMedicationMapper::toMissedMedicationOutputDTO).toList()));
        return missedMedicationOutputDTOS;
    }

    @Override
    public List<MissedMedicationOutputDTO> findByDate(LocalDate date, Patient patient) {
        List<PatientMedication>patientMedications=patientMedicationRepo.findPatientMedicationByPatient(patient);
        List<MissedMedication>missedMedications=missedMedicationRepo.findAllByPatientMedicationInAndDate(patientMedications,date);
        return missedMedications.stream().map(missedMedicationMapper::toMissedMedicationOutputDTO).toList();
    }

}
