package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.ContactScreeningInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.ContactScreeningOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.ContactScreening;
import com.beehyv.tbalert.tbalertbackend.mapper.ContactScreeningMapper;
import com.beehyv.tbalert.tbalertbackend.repository.ContactScreeningRepo;
import com.beehyv.tbalert.tbalertbackend.service.ContactScreeningService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class ContactScreeningServiceImpl implements ContactScreeningService {
    private final ContactScreeningRepo contactScreeningRepo;
    private final ContactScreeningMapper contactScreeningMapper;

    @Override
    public ContactScreeningOutputDTO getContactScreeningById(String patientId) {
        log.info("inside getContactScreeningById");

        ContactScreening contactScreening = contactScreeningRepo.findByPatient_Id(patientId).orElseThrow(()->new IllegalArgumentException("Patient with id " + patientId + " not found"));
        return contactScreeningMapper.toContactScreeningOutputDTO(contactScreening);

    }

    @Override
    public ContactScreeningOutputDTO setContactScreening(ContactScreeningInputDTO contactScreeningInputDTO) {
        log.info("inside saveContactScreening");
        String patientId = contactScreeningInputDTO.getPatientId();
        if(contactScreeningRepo.findByPatient_Id(patientId).isEmpty()) {
            throw new IllegalArgumentException("Patient with id " + patientId + " does not exist");
        }
        ContactScreening contactScreening = contactScreeningMapper.toContactScreening(patientId, contactScreeningInputDTO);
        ContactScreening savedContactScreening = contactScreeningRepo.save(contactScreening);
        return contactScreeningMapper.toContactScreeningOutputDTO(savedContactScreening);
    }

    @Override
    public void deleteContactScreeningById(Integer id) {
        log.info("inside deleteContactScreeningById");

        ContactScreening contactScreening = contactScreeningRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Contact Screening id: " + id));
        contactScreeningRepo.delete(contactScreening);
    }

    @Override
    public ContactScreeningOutputDTO updateContactScreening(String patientId, ContactScreeningInputDTO contactScreeningInputDTO) {
        log.info("inside updateContactScreening");

        ContactScreening contactScreening = contactScreeningRepo.findByPatient_Id(patientId).orElseThrow(() -> new IllegalArgumentException("Patient does not exist with id " + patientId));

        contactScreening.setContactScreeningDone(contactScreeningInputDTO.getContactScreeningDone());
        contactScreening.setDateOfContactScreening(LocalDate.parse(contactScreeningInputDTO.getDateOfContactScreening()));
        contactScreening.setNoOfHHCsAvailable(contactScreeningInputDTO.getNoOfHHCsAvailable());
        contactScreening.setNoOfHHCsScreened(contactScreeningInputDTO.getNoOfHHCsScreened());
        contactScreening.setNoOfHHCsWithTBSymptoms(contactScreeningInputDTO.getNoOfHHCsWithTBSymptoms());
        contactScreening.setNoOfHHCsReferredTBTesting(contactScreeningInputDTO.getNoOfHHCsReferredTBTesting());
        contactScreening.setNoOfHHCsDiagnosedTB(contactScreeningInputDTO.getNoOfHHCsDiagnosedTB());
        contactScreening.setNoOfHHCsTBInitiatedATT(contactScreeningInputDTO.getNoOfHHCsTBInitiatedATT());
        contactScreening.setNoOfHHCsUndergoneLTBITest(contactScreeningInputDTO.getNoOfHHCsUndergoneLTBITest());
        contactScreening.setNoOfEligibleForTPT(contactScreeningInputDTO.getNoOfEligibleForTPT());
        contactScreening.setNoOfHHCsInitiatedTPT(contactScreeningInputDTO.getNoOfHHCsInitiatedTPT());

        ContactScreening updatedContactScreening = contactScreeningRepo.save(contactScreening);

        return contactScreeningMapper.toContactScreeningOutputDTO(updatedContactScreening);
    }

    @Override
    public void deleteContactScreeningByPatientId(String patientId) {
        log.info("inside deleteContactScreeningByPatientId");

        ContactScreening contactScreening = contactScreeningRepo.findByPatient_Id(patientId).orElseThrow(()->new IllegalArgumentException("Patient with id " + patientId + " not found"));
        contactScreeningRepo.delete(contactScreening);
    }
}
