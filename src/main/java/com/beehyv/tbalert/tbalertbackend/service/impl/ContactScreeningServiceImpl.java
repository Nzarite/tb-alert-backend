package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.ContactScreeningInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.ContactScreeningOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.ContactScreening;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.mapper.ContactScreeningMapper;
import com.beehyv.tbalert.tbalertbackend.repository.ContactScreeningRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientRepo;
import com.beehyv.tbalert.tbalertbackend.service.ContactScreeningService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContactScreeningServiceImpl implements ContactScreeningService {
    private final ContactScreeningRepo contactScreeningRepo;
    private final ContactScreeningMapper contactScreeningMapper;
    private final PatientRepo patientRepo;

    @Override
    public ContactScreeningOutputDTO getContactScreeningById(String patientId) {
        ContactScreening contactScreening = contactScreeningRepo.findByPatient_Id(patientId).orElseThrow(()->new RuntimeException("Could not find contact screening"));
        if(contactScreening == null) {
            throw new IllegalArgumentException("Patient does not exist");
        }
        return contactScreeningMapper.toContactScreeningOutputDTO(contactScreening);

    }

    @Override
    public ContactScreeningOutputDTO setContactScreening(Integer patientId, ContactScreeningInputDTO contactScreeningInputDTO) {
        ContactScreening contactScreening = contactScreeningMapper.toContactScreening(patientId, contactScreeningInputDTO);
        if(contactScreening == null) {
            throw new IllegalArgumentException("Patient does not exist");
        }
        contactScreeningRepo.save(contactScreening);
        return contactScreeningMapper.toContactScreeningOutputDTO(contactScreening);
    }

    @Override
    public ContactScreeningOutputDTO updateContactScreening(Integer patientId, ContactScreeningInputDTO contactScreeningInputDTO) {
        Patient patient = patientRepo.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Patient does not exist"));
        ContactScreening contactScreening = contactScreeningRepo.findByPatientId(patientId);

        if(contactScreening == null) {
            throw new IllegalArgumentException("Contact Screening details have not been set");
        }

        contactScreening.setContactScreeningDone(contactScreeningInputDTO.getContactScreeningDone());
        contactScreening.setDateOfContactScreening(contactScreeningInputDTO.getDateOfContactScreening());
        contactScreening.setNoOfHHCsAvailable(contactScreeningInputDTO.getNoOfHHCsAvailable());
        contactScreening.setNoOfHHCsScreened(contactScreeningInputDTO.getNoOfHHCsScreened());
        contactScreening.setNoOfHHCsWithTBSymptoms(contactScreeningInputDTO.getNoOfHHCsWithTBSymptoms());
        contactScreening.setNoOfHHCsReferredTBTesting(contactScreeningInputDTO.getNoOfHHCsReferredTBTesting());
        contactScreening.setNoOfHHCsDiagnosedTB(contactScreeningInputDTO.getNoOfHHCsDiagnosedTB());
        contactScreening.setNoOfHHCsTBInitiatedATT(contactScreeningInputDTO.getNoOfHHCsTBInitiatedATT());
        contactScreening.setNoOfHHCsUndergoneLTBITest(contactScreeningInputDTO.getNoOfHHCsUndergoneLTBITest());
        contactScreening.setNoOfEligibleForTPT(contactScreeningInputDTO.getNoOfEligibleForTPT());
        contactScreening.setNoOfHHCsInitiatedTPT(contactScreeningInputDTO.getNoOfHHCsInitiatedTPT());

        contactScreeningRepo.save(contactScreening);

        return contactScreeningMapper.toContactScreeningOutputDTO(contactScreening);

    }

    @Override
    public void deleteContactScreeningById(Integer id) {
        ContactScreening contactScreening = contactScreeningRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Contact Screening id: " + id));
        contactScreeningRepo.delete(contactScreening);
    }

    @Override
    public void deleteContactScreeningByPatientId(String patientId) {
        ContactScreening contactScreening = contactScreeningRepo.findByPatient_Id(patientId).orElseThrow(()->new RuntimeException("Could not find contact screening"));
        contactScreeningRepo.delete(contactScreening);
    }


}
