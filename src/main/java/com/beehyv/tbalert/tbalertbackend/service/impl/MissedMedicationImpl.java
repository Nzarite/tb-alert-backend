package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.MissedMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MissedMedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.MissedMedication;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.PatientFollowUp;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
import com.beehyv.tbalert.tbalertbackend.mapper.MissedMedicationMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMedicationMapper;
import com.beehyv.tbalert.tbalertbackend.repository.MedicationRepo;
import com.beehyv.tbalert.tbalertbackend.repository.MissedMedicationRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientFollowUpRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientMedicationRepo;
import com.beehyv.tbalert.tbalertbackend.service.MissedMedicationService;
import com.beehyv.tbalert.tbalertbackend.service.PatientFollowUpService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MissedMedicationImpl implements MissedMedicationService {

    private final PatientMapper patientMapper;
    private final MissedMedicationMapper missedMedicationMapper;
    private final MissedMedicationRepo missedMedicationRepo;
    private final PatientMedicationMapper patientMedicationMapper;
    private final PatientMedicationRepo patientMedicationRepo;
    private final PatientFollowUpRepo patientFollowUpRepo;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final MedicationRepo medicationRepo;
    private final PatientFollowUpService patientFollowUpService;


    @Override
    public List<MissedMedicationOutputDTO> add(int id, @Valid List<MissedMedicationInputDTO> missedMedicationInputDTOS) {
        Patient patient=patientMapper.findPatient(id);
        List<MissedMedicationOutputDTO>missedMedicationOutputDTOS=new ArrayList<>();
        missedMedicationInputDTOS.forEach(missedMedicationInputDTO -> {
            PatientMedication patientMedication=patientMedicationRepo.findPatientMedicationByPatientAndMedication(patient,medicationRepo.findById(missedMedicationInputDTO.getMedicationId()).orElseThrow(()->new RuntimeException("Patient Medication not found")));
            if(patientMedication==null) throw new RuntimeException("Patient Medication not founddddd");
            List<MissedMedication> missedMedicationList=missedMedicationRepo.findByPatientMedicationAndDate(patientMedication,LocalDate.parse(missedMedicationInputDTO.getDate(),formatter));
            if(missedMedicationList.isEmpty()) {
                MissedMedication missedMedication=MissedMedication.builder()
                        .patientMedication(patientMedication)
                        .date(LocalDate.parse(missedMedicationInputDTO.getDate(), formatter))
                        .missedDosages(missedMedicationInputDTO.getMissedDoses())
                        .comment(missedMedicationInputDTO.getComment())
                        .build();
                missedMedicationRepo.save(missedMedication);
                patientMedicationRepo.save(missedMedication.getPatientMedication());
                missedMedicationOutputDTOS.add(missedMedicationMapper.toMissedMedicationOutputDTO(missedMedication));
            }
            else {

                missedMedicationList.forEach(missedMedication -> {

                    PatientFollowUp patientFollowUp=patientFollowUpRepo.findByDate(missedMedication.getDate());
                    if(patientFollowUp!=null) {
                        patientFollowUp.setOccured(true);
                        patientFollowUpRepo.save(patientFollowUp);
                    }
                missedMedication.setPatientMedication(patientMedication);
                missedMedication.setComment(missedMedicationInputDTO.getComment());
                missedMedication.setDate(LocalDate.parse(missedMedicationInputDTO.getDate(),formatter));
                missedMedication.setMissedDosages(missedMedicationInputDTO.getMissedDoses());
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
        List<PatientMedication> patientMedications=patientMedicationRepo.findPatientMedicationByPatient(patientMapper.findPatient(id));
        List<MissedMedicationOutputDTO>missedMedicationOutputDTOS=new ArrayList<>();
        patientMedications.forEach(patientMedication -> {
            missedMedicationOutputDTOS.addAll(missedMedicationRepo.findAllByPatientMedication(patientMedication).stream().map(missedMedicationMapper::toMissedMedicationOutputDTO).toList());
        });
        return missedMedicationOutputDTOS;
    }
}
