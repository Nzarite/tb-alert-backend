package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.MissedMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MissedMedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.MissedMedication;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.PatientFollowUp;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
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
    private final MedicationMapper medicationMapper;


    @Override
    public List<MissedMedicationOutputDTO> add(int id, @Valid List<MissedMedicationInputDTO> missedMedicationInputDTOS,LocalDate date) {
        log.info("Service called for adding missed medication: {}", missedMedicationInputDTOS);
        Patient patient=patientMapper.findPatient(id);
        List<MissedMedicationOutputDTO>missedMedicationOutputDTOS=new ArrayList<>();
        missedMedicationInputDTOS.forEach(missedMedicationInputDTO -> {
            PatientMedication patientMedication=patientMedicationRepo.findPatientMedicationByPatientAndMedication(patient,medicationMapper.findMedicationById(missedMedicationInputDTO.getMedicationId()));
            if(patientMedication==null) throw new IllegalArgumentException("Patient Medication not found for patient "+patient.getId()+" medication "+missedMedicationInputDTO.getMedicationId());
            List<MissedMedication> missedMedicationList=missedMedicationRepo.findByPatientMedicationAndDate(patientMedication,date);
            if(missedMedicationList.isEmpty()) {
                MissedMedication missedMedication=missedMedicationMapper.toMissedMedication(missedMedicationInputDTO,patientMedication,date);
                missedMedicationRepo.save(missedMedication);
                patientMedicationRepo.save(missedMedication.getPatientMedication());
                missedMedicationOutputDTOS.add(missedMedicationMapper.toMissedMedicationOutputDTO(missedMedication));
            }
            else {
                log.info("Patient Medication already exist and is being updated");
                missedMedicationList.forEach(missedMedication -> {
                    PatientFollowUp patientFollowUp=patientFollowUpRepo.findByDate(missedMedication.getDate());
                    if(patientFollowUp!=null) {
                        patientFollowUp.setOccured(true);
                        patientFollowUpRepo.save(patientFollowUp);
                    }
                missedMedication.setPatientMedication(patientMedication);
                missedMedication.setComment(missedMedicationInputDTO.getComments());
                missedMedication.setDate(date);
                missedMedication.setMissedDosages(missedMedicationInputDTO.getMissedDosages());
                missedMedicationRepo.save(missedMedication);
                patientMedicationRepo.save(missedMedication.getPatientMedication());
                missedMedicationOutputDTOS.add(missedMedicationMapper.toMissedMedicationOutputDTO(missedMedication));
                });
            }

        });
        return missedMedicationOutputDTOS;
    }

    @Override
    public List<MissedMedicationOutputDTO> get(int id) {
        log.info("Service called for get missed medication: {}", id);
        List<PatientMedication> patientMedications=patientMedicationRepo.findPatientMedicationByPatient(patientMapper.findPatient(id));
        List<MissedMedicationOutputDTO>missedMedicationOutputDTOS=new ArrayList<>();
        patientMedications.forEach(patientMedication -> missedMedicationOutputDTOS.addAll(missedMedicationRepo.findAllByPatientMedication(patientMedication).stream().map(missedMedicationMapper::toMissedMedicationOutputDTO).toList()));
        return missedMedicationOutputDTOS;
    }

    @Override
    public List<MissedMedicationOutputDTO> findByDate(LocalDate date, Patient patient) {
        List<PatientMedication>patientMedications=patientMedicationRepo.findPatientMedicationByPatient(patient);
        List<MissedMedicationOutputDTO>missedMedicationOutputDTOS=new ArrayList<>();
        patientMedications.forEach(patientMedication -> missedMedicationOutputDTOS.addAll(missedMedicationRepo.findByPatientMedicationAndDate(patientMedication,date).stream().map(missedMedicationMapper::toMissedMedicationOutputDTO).toList()));
        return missedMedicationOutputDTOS;
    }

}
