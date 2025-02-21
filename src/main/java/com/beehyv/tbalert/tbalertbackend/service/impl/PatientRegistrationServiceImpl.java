package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.PatientUpdateInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Address;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.PatientFollowUp;
import com.beehyv.tbalert.tbalertbackend.entity.Person;
import com.beehyv.tbalert.tbalertbackend.mapper.LocalDateMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PersonMapper;
import com.beehyv.tbalert.tbalertbackend.repository.*;
import com.beehyv.tbalert.tbalertbackend.service.PatientFollowUpService;
import com.beehyv.tbalert.tbalertbackend.service.PatientRegistrationService;
import com.beehyv.tbalert.tbalertbackend.service.PersonService;
import com.beehyv.tbalert.tbalertbackend.specifications.PatientSpecification;
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
    private final AddressRepo addressRepo;
    private final ContactScreeningRepository contactScreeningRepo;
    private final PatientFollowUpService patientFollowUpService;
    private final PersonMapper personMapper;
    private final LocalDateMapper localDateMapper;
    private final PersonRepo personRepo;
    private final PersonService personService;
    private final PatientSpecification patientSpecification;
    private final NikshayMitraRepo nikshayMitraRepo;

    @Override
    public PatientOutputDTO register(PatientInputDTO patientInputDTO) {
        log.info("Service called to Register patient: {}", patientInputDTO);
        PersonOutputDTO person = personService.add(patientInputDTO);
        Patient patient = new Patient();
        patient.setPerson(personMapper.find(person.getId()));
        patient.setAge(patientInputDTO.getAge());
        String state = person.getState();
        String id = switch (state) {
            case "TELANGANA" -> "TG";
            case "UTTAR PRADESH" -> "UP";
            case "BIHAR" -> "BH";
            default -> "";
        };
        long currCnt = patientRepo.count();
        id += currCnt;
        patient.setId(id);
        patientRepo.save(patient);

        return patientMapper.toPatientOutputDTO(patient);
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

        Address address = person.getAddress();
        if (patientUpdateInputDTO.getBlock() != null)
            address.setBlock(patientUpdateInputDTO.getBlock());
        if (patientUpdateInputDTO.getState() != null)
            address.setState(patientUpdateInputDTO.getState());
        if (patientUpdateInputDTO.getGp() != null)
            address.setGp(patientUpdateInputDTO.getGp());
        if (patientUpdateInputDTO.getDistrict() != null)
            address.setDistrict(patientUpdateInputDTO.getDistrict());
        if (patientUpdateInputDTO.getVillage() != null)
            address.setVillage(patientUpdateInputDTO.getVillage());

        if (patientUpdateInputDTO.getCurrentStatus() != null)
            patient.setCurrentStatus(patientUpdateInputDTO.getCurrentStatus());
        if (patientUpdateInputDTO.getAge() > 0)
            patient.setAge(patientUpdateInputDTO.getAge());

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
        contactScreeningRepo.deleteByPatientId(patientId);
        patientRepo.save(patient);
    }

    @Override
    public List<PatientOutputDTO> getAll() {
        log.info("Service getAll patients");
        return patientRepo.findByPerson_IsDeletedFalse().stream()
                .map(patientMapper::toPatientOutputDTO)
                .toList();
    }

    @Override
    public List<PatientOutputDTO> getPatientByNameOrNikshayIdOrPatientId(String patientName) {
        log.info("Service getPatientByNameOrNikshayIdOrPatientId patientName: {}", patientName);
        return patientRepo.findAllByPatientIdOrNameOrNikshayId(patientName).stream().map(patientMapper::toPatientOutputDTO).toList();
    }

    @Override
    public List<PatientOutputDTO> getFilteredPatients(Map<String, Object> filters) {
        log.info("Service getFilteredPatients filters: {}", filters);
        Specification<Patient> specification = patientSpecification.getPatientsByFilter(filters);
        List<Patient> patients = patientRepo.findAll(specification);
        return patients.stream().map(patientMapper::toPatientOutputDTO).toList();
    }

    @Override
    public String determinePatientStatus(PatientOutputDTO patient) {
        if (patient.getCurrentStatus() != null) {
            if ("dead".equals(patient.getCurrentStatus())) return "Dead";
            if (patient.isCured()) return "Cured";
        }
        List<PatientFollowUp> followUps = patientFollowUpService.findBeforeDate(patient.getPatientId(), LocalDate.now());
        return followUps.stream().skip(Math.max(followUps.size() - 3, 0)).anyMatch(PatientFollowUp::getOccured) ? "Treatment ongoing" : "No Contact";
    }
}
