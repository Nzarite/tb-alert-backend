package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.MissedMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.PatientFollowUpInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.PatientMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.TBDetailsInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.TBDetailsOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.TBDetails;
import com.beehyv.tbalert.tbalertbackend.mapper.LocalDateMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.TBDetailsMapper;
import com.beehyv.tbalert.tbalertbackend.repository.TBDetailsRepo;
import com.beehyv.tbalert.tbalertbackend.service.MissedMedicationService;
import com.beehyv.tbalert.tbalertbackend.service.PatientFollowUpService;
import com.beehyv.tbalert.tbalertbackend.service.PatientMedicationService;
import com.beehyv.tbalert.tbalertbackend.service.TBDetailsService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class TBDetailsServiceImpl implements TBDetailsService {

    private final TBDetailsRepo tbDetailsRepo;
    private final TBDetailsMapper tbDetailsMapper;
    private final LocalDateMapper localDateMapper;
    private final PatientMapper patientMapper;
    private final PatientFollowUpService patientFollowUpService;
    private final PatientMedicationService patientMedicationService;
    private final MissedMedicationService missedMedicationService;

    @Override
    public TBDetailsOutputDTO getTBDetails(String patientId) {

        log.info("inside getTBDetails");

        TBDetails tbDetails = tbDetailsRepo.findByPatient_Id(patientId).orElseThrow(() -> new IllegalArgumentException("" +
                "Patient with id " + patientId + " not found"));

        return tbDetailsMapper.toOutputDto(tbDetails);
    }

    @Override
    public TBDetailsOutputDTO registerTBDetails(TBDetailsInputDTO tbDetailsInputDTO) {

        log.info("inside registerTBDetails");

        TBDetails tbDetails = tbDetailsMapper.toTBDetails(tbDetailsInputDTO);
        tbDetailsRepo.save(tbDetails);

        Patient patient=patientMapper.findPatient(tbDetails.getPatient().getId());
        LocalDate localDate = LocalDate.now();
        int curr=15;
        List<PatientMedicationInputDTO> patientMedicationInputDTO=List.of(PatientMedicationInputDTO
                .builder()
                .medicationId(1)
                .frequency(1)
                .build());
        List<MissedMedicationInputDTO> missedMedicationInputDTO=List.of(MissedMedicationInputDTO
                .builder()
                .medicationId(1)
                .missedDosages(0)
                .comments("")
                .build());
        patientMedicationService.add(patient.getId(),patientMedicationInputDTO);
        for (int i = 0; i < 8; i++)
        {
            PatientFollowUpInputDTO patientFollowUpInputDTO = PatientFollowUpInputDTO.builder()
                    .date(localDate.toString())
                    .followUpStatus("Missed")
                    .remarks("")
                    .build();
            missedMedicationService.add(patient.getId(),missedMedicationInputDTO,localDate);
            patientFollowUpService.add(patient.getId(), patientFollowUpInputDTO);
            localDate = localDate.plusDays(curr);
            if(i==2) curr=30;
        }

        return tbDetailsMapper.toOutputDto(tbDetails);
    }

    @Override
    public TBDetailsOutputDTO updateTBDetails(TBDetailsInputDTO tbDetailsInputDTO, String patientId) {

        log.info("inside updateTBDetails");

        TBDetails tbDetails = tbDetailsRepo.findByPatient_Id(patientId).orElseThrow(() -> new IllegalArgumentException("" +
                "Patient with id " + patientId + " not found"));

        tbDetails.setDateOfDiagnosis(localDateMapper.toLocalDate(tbDetailsInputDTO.getDateOfDiagnosis()));
        tbDetails.setDateOfTreatmentInitiation(localDateMapper.toLocalDate(tbDetailsInputDTO.getDateOfTreatmentInitiation()));
        tbDetails.setTypeOfPwtb(tbDetailsInputDTO.getTypeOfPwtb());
        tbDetails.setTypeOfTb(tbDetailsInputDTO.getTypeOfTb());
        tbDetails.setDstbOrDrtb(tbDetailsInputDTO.getDstbOrDrtb());
        TBDetails updatedTBDetails = tbDetailsRepo.save(tbDetails);

        return tbDetailsMapper.toOutputDto(updatedTBDetails);
    }

    @Override
    public void deleteTBDetails(String patientId) {

        log.info("inside deleteTBDetails");

        TBDetails tbDetails = tbDetailsRepo.findByPatient_Id(patientId).orElseThrow(() -> new IllegalArgumentException("" +
                "Patient with id " + patientId + " not found"));

        tbDetailsRepo.delete(tbDetails);
    }

}
