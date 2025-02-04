package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientFollowUpInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.model.MedicationDetails;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.MissedMedication;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.PatientFollowUp;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
import com.beehyv.tbalert.tbalertbackend.mapper.MedicationDetailsMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMapper;
import com.beehyv.tbalert.tbalertbackend.repository.MissedMedicationRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientFollowUpRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientMedicationRepo;
import com.beehyv.tbalert.tbalertbackend.service.PatientFollowUpService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientFollowUpServiceImpl implements PatientFollowUpService {

    private final PatientFollowUpRepo patientFollowUpRepo;
    private final PatientMapper patientMapper;
    private final MedicationDetailsMapper medicationDetailsMapper;
    private final MissedMedicationRepo missedMedicationRepo;
    private final PatientMedicationRepo patientMedicationRepo;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Override
    public List<PatientFollowUpOutputDTO> get(int id) {
        List<PatientFollowUp> patientFollowUps = patientFollowUpRepo.findByPatient_Id(id);
        List<PatientFollowUpOutputDTO>patientFollowUpOutputDTOS = new ArrayList<>();
        patientFollowUps.forEach(patientFollowUp -> {
            List<PatientMedication>patientMedications=patientMedicationRepo.getPatientMedicationsByPatient(patientFollowUp.getPatient());
            List<MissedMedication>missedMedicationList=new ArrayList<>();
            patientMedications.forEach(patientMedication -> {
                List<MissedMedication>missedMedications=missedMedicationRepo.findByPatientMedicationAndDate(patientMedication,patientFollowUp.getDate());
                missedMedicationList.addAll(missedMedications);
            });
            patientFollowUpOutputDTOS.add(
                    PatientFollowUpOutputDTO.builder()
                    .patient(patientMapper.toPatientOutputDTO(patientFollowUp.getPatient()))
                    .medicationDetails(missedMedicationList.stream().map(medicationDetailsMapper::toMedicationDetails).collect(Collectors.toList()))
                    .remarks(patientFollowUp.getRemarks())
                    .date(patientFollowUp.getDate().toString())
                    .followUpStatus(patientFollowUp.getOccured() != null && patientFollowUp.getOccured())
                    .build());
        });
        return patientFollowUpOutputDTOS;
    }

    @Override
    public PatientFollowUpOutputDTO add(int id, PatientFollowUpInputDTO patientFollowUpInputDTO) {
        Patient patient=patientMapper.findPatient(id);
        System.out.println(patientFollowUpInputDTO);
        List<PatientMedication> patientMedications=patientMedicationRepo.findPatientMedicationByPatient(patient);
        List<MissedMedication> missedMedicationList=new ArrayList<>();
        patientMedications.forEach(patientMedication -> {
            missedMedicationList.addAll((missedMedicationRepo.findByPatientMedicationAndDate(patientMedication, LocalDate.parse(patientFollowUpInputDTO.getDate(), formatter))));
        });
        PatientFollowUp patientFollowUp=PatientFollowUp.builder()
                                                        .patient(patient)
                                                        .missedMedicationList(missedMedicationList)
                                                        .date(LocalDate.parse(patientFollowUpInputDTO.getDate(),formatter))
                                                        .remarks(patientFollowUpInputDTO.getRemarks())
                                                        .occured(true)
                                                        .build();
        patientFollowUpRepo.save(patientFollowUp);
        return PatientFollowUpOutputDTO.builder()
                .patient(patientMapper.toPatientOutputDTO(patient))
                .medicationDetails(missedMedicationList.stream()
                        .map((medicationDetailsMapper::toMedicationDetails)).collect(Collectors.toList()))
                .remarks(patientFollowUp.getRemarks())
                .date(patientFollowUp.getDate().toString())
                .followUpStatus(patientFollowUp.getOccured())
                .build();

    }
}
