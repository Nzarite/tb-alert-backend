package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.ContactScreeningDTO;
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

    public ContactScreening toContactScreening(ContactScreeningDTO contactScreeningDTO) {
        Patient patient = patientRepo.findById(contactScreeningDTO.getPatientId()).orElseThrow(() -> new IllegalArgumentException("Invalid Patient ID: " + contactScreeningDTO.getPatientId()));

        return ContactScreening.builder()
                .id(contactScreeningDTO.getId())
                .contactScreeningDone(contactScreeningDTO.getContactScreeningDone())
                .dateOfContactScreening(contactScreeningDTO.getDateOfContactScreening())
                .noOfHHCsAvailable(contactScreeningDTO.getNoOfHHCsAvailable())
                .noOfHHCsScreened(contactScreeningDTO.getNoOfHHCsScreened())
                .noOfHHCsWithTBSymptoms(contactScreeningDTO.getNoOfHHCsWithTBSymptoms())
                .noOfHHCsReferredTBTesting(contactScreeningDTO.getNoOfHHCsReferredTBTesting())
                .noOfHHCsDiagnosedTB(contactScreeningDTO.getNoOfHHCsDiagnosedTB())
                .noOfHHCsTBInitiatedATT(contactScreeningDTO.getNoOfHHCsTBInitiatedATT())
                .noOfHHCsUndergoneLTBITest(contactScreeningDTO.getNoOfHHCsUndergoneLTBITest())
                .noOfEligibleForTPT(contactScreeningDTO.getNoOfEligibleForTPT())
                .noOfHHCsInitiatedTPT(contactScreeningDTO.getNoOfHHCsInitiatedTPT())
                .patient(patient)
                .build();
    }

    public ContactScreeningDTO toContactScreeningDTO(ContactScreening contactScreening) {
        return ContactScreeningDTO.builder()
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
