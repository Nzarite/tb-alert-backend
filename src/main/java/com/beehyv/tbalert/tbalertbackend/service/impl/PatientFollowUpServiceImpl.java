package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientFollowUpInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputForFrontEndDto;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputForFrontEndDto.FollowUpDetails;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.MissedMedication;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.PatientFollowUp;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
import com.beehyv.tbalert.tbalertbackend.mapper.LocalDateMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.MissedMedicationMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientFollowUpMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMapper;
import com.beehyv.tbalert.tbalertbackend.repository.MissedMedicationRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientFollowUpRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientMedicationRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientRepo;
import com.beehyv.tbalert.tbalertbackend.service.MissedMedicationService;
import com.beehyv.tbalert.tbalertbackend.service.PatientFollowUpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PatientFollowUpServiceImpl implements PatientFollowUpService {

    private final PatientFollowUpRepo patientFollowUpRepo;
    private final PatientMapper patientMapper;
    private final MissedMedicationRepo missedMedicationRepo;
    private final PatientMedicationRepo patientMedicationRepo;
    private final PatientFollowUpMapper patientFollowUpMapper;
    private MissedMedicationService missedMedicationService;
    private final PatientRepo patientRepo;
    private final MissedMedicationMapper missedMedicationMapper;
    private final LocalDateMapper localDateMapper;


    @Override
    public PatientFollowUpOutputForFrontEndDto get(String id) {
        log.info("Service called to Get patient follow up with patient id {}", id);
        List<PatientFollowUp> patientFollowUps = patientFollowUpRepo.findByPatient_Id(id);

        Patient patient = patientMapper.findPatient(id);
        List<PatientFollowUpOutputForFrontEndDto.FollowUpDetails>followUpDetails=new ArrayList<>();

        List<PatientMedication>patientMedications=patientMedicationRepo.getPatientMedicationsByPatient(patient);

        patientFollowUps.forEach(patientFollowUp -> {
            List<MissedMedication>missedMedicationList=new ArrayList<>();
            patientMedications.forEach(patientMedication -> {
                List<MissedMedication>missedMedications=missedMedicationRepo.findByPatientMedicationAndDate(patientMedication,patientFollowUp.getDate());
                missedMedicationList.addAll(missedMedications);
            });
            FollowUpDetails followUpDetail=patientFollowUpMapper.toFollowUpDetails(patientFollowUp,missedMedicationList);
            followUpDetails.add(followUpDetail);
        });
        return PatientFollowUpOutputForFrontEndDto.builder()
                .followUpDetails(followUpDetails)
                .patient(patientMapper.toPatientOutputDTO(patient))
                .build();
    }

    @Override
    public PatientFollowUpOutputDTO add(String id, PatientFollowUpInputDTO patientFollowUpInputDTO) {
        log.info("Service called to Add patient follow up with patient id {}", id);
        Patient patient=patientMapper.findPatient(id);
        List<MissedMedication>missedMedicationList=missedMedicationMapper.findMissedMedicationsByPatientandDate(patient,localDateMapper.toLocalDate(patientFollowUpInputDTO.getDate()));
        PatientFollowUp patientFollowUp=patientFollowUpMapper.toPatientFollowUp(patientFollowUpInputDTO,patient);
        patientFollowUpRepo.save(patientFollowUp);
        return patientFollowUpMapper.toDTO(patientFollowUp,missedMedicationList);
    }

    @Override
    public PatientFollowUpOutputDTO update(String id, PatientFollowUpInputDTO patientFollowUpInputDTO) {
        LocalDate date=localDateMapper.toLocalDate(patientFollowUpInputDTO.getDate());
        missedMedicationService.add(id,patientFollowUpInputDTO.getMissedMedications(),date);
        Patient patient=patientMapper.findPatient(id);
        PatientFollowUp patientFollowUp=patientFollowUpRepo.findByPatientAndDate(patient,date);
        if(patientFollowUp==null){
            throw new IllegalArgumentException("No follow up exists for the given patient and date");
        }

        patientFollowUp.setRemarks(patientFollowUpInputDTO.getRemarks());
        patientFollowUp.setDate(date);
        if(patientFollowUpInputDTO.getAliveOrDead()!=null){
            patient.setCurrentStatus(patientFollowUpInputDTO.getAliveOrDead());

        }
        if(patientFollowUpInputDTO.isCured()){
            patient.setCured(true);
        }
        if((patientFollowUpInputDTO.getAliveOrDead()!=null && patientFollowUpInputDTO.getAliveOrDead().equals("dead"))||(patientFollowUpInputDTO.isCured())){
            List<PatientFollowUp>followUps=patientFollowUpRepo.findAllByPatient_Id(patient.getId());
            for(PatientFollowUp followUp:followUps){
                if(followUp.getDate().equals(date) || followUp.getDate().isAfter(date))
                    followUp.setStatus("Cancelled");
            }
            patientFollowUpRepo.saveAll(followUps.stream().toList());
        }
        else {
            patientFollowUp.setStatus("Occured");
            if((patient.isCured() && !patientFollowUpInputDTO.isCured()) || (patient.getCurrentStatus().equals("dead") && patientFollowUpInputDTO.getAliveOrDead().equals("alive"))){
                List<PatientFollowUp>followUps=patientFollowUpRepo.findAllByPatient_Id(patient.getId());
                for(PatientFollowUp followUp:followUps){
                    if(followUp.getStatus().equals("Cancelled"))
                        followUp.setStatus("Missed");
                }
            }
        }
        patientFollowUp.setPatientCondition(patientFollowUpInputDTO.getPatientCondition());
        patientFollowUpRepo.save(patientFollowUp);
        List<MissedMedication>missedMedications=missedMedicationMapper.findMissedMedicationsByPatientandDate(patient,patientFollowUp.getDate());

        patientRepo.save(patient);

        return patientFollowUpMapper.toDTO(patientFollowUp,missedMedications);
    }

    @Override
    public List<PatientFollowUp> findBeforeDate(String id, LocalDate localDate) {
        Patient patient=patientMapper.findPatient(id);
        return patientFollowUpRepo.findByPatientAndDateBefore(patient,localDate);
    }

    @Override
    public List<PatientFollowUpOutputForFrontEndDto> getFollowUpForPatientList(List<PatientOutputDTO> patientList) {
        List<PatientFollowUpOutputForFrontEndDto>patientFollowUpOutputForFrontEndDtos=new ArrayList<>();
        for(PatientOutputDTO patient:patientList){
            patientFollowUpOutputForFrontEndDtos.add(get(patient.getPatientId()));
        }
        return patientFollowUpOutputForFrontEndDtos;
    }
}
