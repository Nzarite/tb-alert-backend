package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.PatientUpdateInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.*;
import com.beehyv.tbalert.tbalertbackend.mapper.GramPanchayatMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PersonMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.StateMapper;
import com.beehyv.tbalert.tbalertbackend.repository.PatientRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PersonRepo;
import com.beehyv.tbalert.tbalertbackend.repository.TBDetailsRepo;
import com.beehyv.tbalert.tbalertbackend.service.*;
import com.beehyv.tbalert.tbalertbackend.specifications.TBDetailsSpecification;
import com.beehyv.tbalert.tbalertbackend.util.UserDetailsUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class PatientRegistrationServiceImpl implements PatientRegistrationService {

    private final PatientRepo patientRepo;
    private final PatientMapper patientMapper;
    private final PatientFollowUpService patientFollowUpService;
    private final PersonMapper personMapper;
    private final PersonRepo personRepo;
    private final PersonService personService;
    private final TBDetailsSpecification tbDetailsSpecification;
    private final ContactScreeningService contactScreeningService;
    private final NikshayMitraService nikshayMitraService;
    private final PatientMedicationService patientMedicationService;
    private final StateMapper stateMapper;
    private final TBDetailsRepo tbDetailsRepo;
    private final UserDetailsUtil userDetailsUtil;
    private final GramPanchayatMapper gramPanchayatMapper;

    @Override
    public PatientOutputDTO register(PatientInputDTO patientInputDTO) {
        log.info("Service called to register patient");

        PersonOutputDTO person = personService.add(patientInputDTO);

        Patient patient = new Patient();
        patient.setPerson(personMapper.find(person.getId()));
        patient.setAge(patientInputDTO.getAge());
        patient.setConsentForMessage(patientInputDTO.getConsentForMessage());
        patient.setReminderTime(patientInputDTO.getReminderTime());
        patient.setIsDiagnosedWithTB(patientInputDTO.getIsDiagnosedWithTB());

        String stateName = person.getState();
        State state = stateMapper.getStateByName(stateName);

        long currCnt = patientRepo.count();
        String id = state.getStateCode() + currCnt;
        patient.setId(id);

        Patient savedPatient = patientRepo.save(patient);
        return patientMapper.toPatientOutputDTO(savedPatient);
    }

    @Override
    public PatientOutputDTO getPatient(String patientId) {
        log.info("Service called to retrieve patient with Id: {}", patientId);

        Patient patient = patientMapper.find(patientId);
        return patientMapper.toPatientOutputDTO(patient);
    }


    @Override
    public void updatePatient(String patientId, PatientUpdateInputDTO patientUpdateInputDTO) {
        log.info("Service called to update patient with Id: {}", patientId);

        Patient patient = patientMapper.find(patientId);

        // Person specific details
        Person person = patient.getPerson();

        person.setUpdatedBy(patientUpdateInputDTO.getUpdatedBy());
        if (patientUpdateInputDTO.getFirstName() != null)
            person.setFirstName(patientUpdateInputDTO.getFirstName());
        if (patientUpdateInputDTO.getLastName() != null)
            person.setLastName(patientUpdateInputDTO.getLastName());
        if (patientUpdateInputDTO.getGender() != null)
            person.setGender(patientUpdateInputDTO.getGender());
        if (patientUpdateInputDTO.getPhoneNumber() != null)
            person.setPhoneNumber(patientUpdateInputDTO.getPhoneNumber());
        if (patientUpdateInputDTO.getEmail() != null)
            person.setEmail(patientUpdateInputDTO.getEmail());

        // Address specific details
        Address address = person.getAddress();

        if(patientUpdateInputDTO.getGramPachayatId() != null) {
            GramPanchayat gramPanchayat=gramPanchayatMapper.getGramPanchayatById(patientUpdateInputDTO.getGramPachayatId());
            address.setGramPanchayat(gramPanchayat);
        }

        // Patient specific details
        if (patientUpdateInputDTO.getCurrentStatus() != null)
            patient.setCurrentStatus(patientUpdateInputDTO.getCurrentStatus());
        if (patientUpdateInputDTO.getAge() > 0)
            patient.setAge(patientUpdateInputDTO.getAge());
        if (patientUpdateInputDTO.getConsentForMessage() != null)
            patient.setConsentForMessage(patientUpdateInputDTO.getConsentForMessage());
        if (patientUpdateInputDTO.getIsDiagnosedWithTB() != null)
            patient.setIsDiagnosedWithTB(patientUpdateInputDTO.getIsDiagnosedWithTB());

        if (patientUpdateInputDTO.getReminderTime() != null)
            patient.setReminderTime(patientUpdateInputDTO.getReminderTime());

        person.setAddress(address);
        person = personRepo.save(person);
        patient.setPerson(person);

        patientRepo.save(patient);
    }

    @Override
    public void deletePatient(String patientId) {
        log.info("Service called to delete patient with Id: {}", patientId);

        Patient patient = patientMapper.find(patientId);
        patient.getPerson().setIsDeleted(true);
        patient.getPerson().setEmail(null);
        personRepo.save(patient.getPerson());
        nikshayMitraService.deleteNikshayDetails(patientId);
        contactScreeningService.deleteContactScreeningByPatientId(patientId);
        patientMedicationService.delete(patientId);
        patientFollowUpService.delete(patientId);
        patientRepo.save(patient);
    }

    @Override
    public List<PatientOutputDTO> getAllNotDeleted() {
        log.info("Service getAllNotDeleted patients");

        return patientRepo.findAllByPerson_IsDeletedFalse()
                .stream()
                .map(patientMapper::toPatientOutputDTO)
                .toList();
    }

    @Override
    public List<PatientOutputDTO> getAll() {
        log.info("Service getAll patients");

        return patientRepo.findAll()
                .stream()
                .map(patientMapper::toPatientOutputDTO)
                .toList();
    }

    @Override
    public List<PatientOutputDTO> getPatientByNameOrNikshayIdOrPatientId(String patientName) {
        log.info("Service getPatientByNameOrNikshayIdOrPatientId patientName: {}", patientName);

        List<String> userRoles = userDetailsUtil.getUserRolesFromKeycloak();
        boolean onlyShowDiagnosedWithTB = userRoles.size() == 1 && userRoles.getFirst().equals("Telecaller");

        return patientRepo.findAllByPatientIdOrNameOrNikshayId(patientName, onlyShowDiagnosedWithTB)
                .stream()
                .map(patientMapper::toPatientOutputDTO)
                .toList();
    }

    @Override
    public List<PatientOutputDTO> getFilteredPatients(Map<String, Object> filters) {
        log.info("Service getFilteredPatients filters: {}", filters);

        Specification<TBDetails> specification = tbDetailsSpecification.getPatientsByFilter(filters);
        List<Patient> patients = tbDetailsRepo.findAll(specification)
                .stream()
                .map(TBDetails::getPatient)
                .toList();

        return patients
                .stream()
                .map(patientMapper::toPatientOutputDTO)
                .toList();
    }

    @Override
    public String determinePatientStatus(PatientOutputDTO patient) {
        log.info("Service determinePatientStatus patient: {}", patient);

        if (patient.getCurrentStatus() != null) {
            if ("dead".equals(patient.getCurrentStatus())) return "Dead";
            if (patient.isCured()) return "Cured";
        }
        List<PatientFollowUp> followUps = patientFollowUpService.findBeforeDate(patient.getPatientId(), LocalDate.now());
        String treatmentStatus;
        boolean recentOccurrence = followUps.stream()
                .skip(Math.max(followUps.size() - 3, 0))
                .anyMatch(patientFollowUp -> patientFollowUp.getStatus().equals("Occured"));

        if (followUps.isEmpty() || recentOccurrence) {
            treatmentStatus = "Treatment ongoing";
        } else if (followUps.getLast().getStatus().equals("Cancelled")) {
            treatmentStatus = "Treatment cancelled";
        } else {
            treatmentStatus = "No Contact";
        }

        return treatmentStatus;
    }

    @Override
    public List<PatientOutputDTO> getAllByState(String state) {
        log.info("Service getAllByState state: {}", state);

        List<Patient> patients = patientRepo.findAllByPerson_Address_GramPanchayat_Mandal_District_State_StateNameAndPerson_IsDeletedFalse(state);
        return patients.stream()
                .map(patientMapper::toPatientOutputDTO)
                .toList();
    }

    @Override
    public List<PatientOutputDTO> getPatientByState(String state, String name) {
        log.info("Service getPatientByState state: {}, name: {}", state, name);

        List<Patient> patients = patientRepo.findPatientByNameAndState(name, state);
        return patients.stream()
                .map(patientMapper::toPatientOutputDTO)
                .toList();
    }
}
