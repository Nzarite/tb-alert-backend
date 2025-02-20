package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.ContactScreeningInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.ContactScreeningOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.ContactScreening;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.repository.PatientRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ContactScreeningMapper {

    private final PatientRepo patientRepo;

    public ContactScreening toContactScreening(Integer patientId, ContactScreeningInputDTO contactScreeningInputDTO) {
        Patient patient = patientRepo.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Invalid Patient ID: " + patientId));

        if(!contactScreeningInputDTO.getContactScreeningDone()){
            return ContactScreening.builder()
                    .contactScreeningDone(false)
                    .patient(patient)
                    .build();
        }

        return ContactScreening.builder()
                .contactScreeningDone(true)
                .dateOfContactScreening(contactScreeningInputDTO.getDateOfContactScreening())
                .noOfHHCsAvailable(contactScreeningInputDTO.getNoOfHHCsAvailable())
                .noOfHHCsScreened(contactScreeningInputDTO.getNoOfHHCsScreened())
                .noOfHHCsWithTBSymptoms(contactScreeningInputDTO.getNoOfHHCsWithTBSymptoms())
                .noOfHHCsReferredTBTesting(contactScreeningInputDTO.getNoOfHHCsReferredTBTesting())
                .noOfHHCsDiagnosedTB(contactScreeningInputDTO.getNoOfHHCsDiagnosedTB())
                .noOfHHCsTBInitiatedATT(contactScreeningInputDTO.getNoOfHHCsTBInitiatedATT())
                .noOfHHCsUndergoneLTBITest(contactScreeningInputDTO.getNoOfHHCsUndergoneLTBITest())
                .noOfEligibleForTPT(contactScreeningInputDTO.getNoOfEligibleForTPT())
                .noOfHHCsInitiatedTPT(contactScreeningInputDTO.getNoOfHHCsInitiatedTPT())
                .patient(patient)
                .build();
    }

    public ContactScreeningOutputDTO toContactScreeningOutputDTO(ContactScreening contactScreening) {
        return ContactScreeningOutputDTO.builder()
                .id(contactScreening.getId())
                .contactScreeningDone(contactScreening.getContactScreeningDone())
                .dateOfContactScreening(contactScreening.getDateOfContactScreening())
                .noOfHHCsAvailable(contactScreening.getNoOfHHCsAvailable())
                .noOfHHCsScreened(contactScreening.getNoOfHHCsScreened())
                .noOfHHCsWithTBSymptoms(contactScreening.getNoOfHHCsWithTBSymptoms())
                .noOfHHCsReferredTBTesting(contactScreening.getNoOfHHCsReferredTBTesting())
                .noOfHHCsDiagnosedTB(contactScreening.getNoOfHHCsDiagnosedTB())
                .noOfHHCsTBInitiatedATT(contactScreening.getNoOfHHCsTBInitiatedATT())
                .noOfHHCsUndergoneLTBITest(contactScreening.getNoOfHHCsUndergoneLTBITest())
                .noOfEligibleForTPT(contactScreening.getNoOfEligibleForTPT())
                .noOfHHCsInitiatedTPT(contactScreening.getNoOfHHCsInitiatedTPT())
                .patientId(contactScreening.getPatient().getId())
                .build();
    }

}
