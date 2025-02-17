package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientFollowUpInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.PatientUpdateInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Address;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.PatientFollowUp;
import com.beehyv.tbalert.tbalertbackend.entity.Person;
import com.beehyv.tbalert.tbalertbackend.mapper.LocalDateMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PersonMapper;
import com.beehyv.tbalert.tbalertbackend.repository.AddressRepo;
import com.beehyv.tbalert.tbalertbackend.repository.ContactScreeningRepository;
import com.beehyv.tbalert.tbalertbackend.repository.PatientRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PersonRepo;
import com.beehyv.tbalert.tbalertbackend.service.PatientFollowUpService;
import com.beehyv.tbalert.tbalertbackend.service.PatientRegistrationService;
import com.beehyv.tbalert.tbalertbackend.service.PersonService;
import com.beehyv.tbalert.tbalertbackend.specifications.PatientSpecification;
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

    @Override
    public PatientOutputDTO register(PersonInputDTO personInputDTO) {
        log.info("Service called to Register patient: {}", personInputDTO);
        PersonOutputDTO person = personService.add(personInputDTO);
        Patient patient=new Patient();
        patient.setPerson(personMapper.find(person.getId()));
        patientRepo.save(patient);
        LocalDate localDate = LocalDate.now();
        int curr=15;
        for (int i = 0; i < 8; i++)
        {
            PatientFollowUpInputDTO patientFollowUpInputDTO = PatientFollowUpInputDTO.builder()
                    .date(localDate.toString())
                    .remarks("")
                    .build();
            patientFollowUpService.add(patient.getId(), patientFollowUpInputDTO);
            localDate = localDate.plusDays(curr);
            if(i==2) curr=30;
        }
        return patientMapper.toPatientOutputDTO(patient);
    }

    @Override
    public PatientOutputDTO getPatient(int patientId) {
        log.info("Service called to retrieve patient with Id: {}", patientId);
        Patient patient=patientMapper.findPatient(patientId);
        return patientMapper.toPatientOutputDTO(patient);
    }

    @Override
    public void updatePatient(int patientId, PatientUpdateInputDTO patientUpdateInputDTO) {
        log.info("Service called to update patient with Id: {}", patientId);

        Patient patient=patientMapper.findPatient(patientId);
        Person person=patient.getPerson();

        person.setUpdatedBy(patientUpdateInputDTO.getUpdatedBy());
        if(patientUpdateInputDTO.getFirstName()!=null)
            person.setFirstName(patientUpdateInputDTO.getFirstName());
        if(patientUpdateInputDTO.getLastName()!=null)
            person.setLastName(patientUpdateInputDTO.getLastName());
        if(patientUpdateInputDTO.getGender()!=null)
            person.setGender(patientUpdateInputDTO.getGender());
        if(patientUpdateInputDTO.getPhoneNumber()!=null)
            person.setPhoneNumber(patientUpdateInputDTO.getPhoneNumber());
        if(patientUpdateInputDTO.getEmail()!=null)
            person.setEmail(patientUpdateInputDTO.getEmail());
        if(patientUpdateInputDTO.getDateOfBirth()!=null)
            person.setDateOfBirth(localDateMapper.toLocalDate(patientUpdateInputDTO.getDateOfBirth()));

        Address address=person.getAddress();
        if(patientUpdateInputDTO.getBlock()!=null)
            address.setBlock(patientUpdateInputDTO.getBlock());
        if(patientUpdateInputDTO.getState()!=null)
            address.setState(patientUpdateInputDTO.getState());
        if(patientUpdateInputDTO.getGp()!=null)
            address.setGp(patientUpdateInputDTO.getGp());
        if(patientUpdateInputDTO.getDistrict()!=null)
            address.setDistrict(patientUpdateInputDTO.getDistrict());
        if(patientUpdateInputDTO.getVillage()!=null)
            address.setVillage(patientUpdateInputDTO.getVillage());

        if(patientUpdateInputDTO.getCurrentStatus()!=null)
            patient.setCurrentStatus(patientUpdateInputDTO.getCurrentStatus());

        person.setAddress(address);
        person=personRepo.save(person);
        patient.setPerson(person);
        
        patientRepo.save(patient);

    }

    @Override
    public void deletePatient(int patientId) {
        log.info("Service called to delete patient with Id: {}", patientId);
        Patient patient=patientMapper.findPatient(patientId);
        contactScreeningRepo.deleteByPatientId(patientId);
        patientRepo.delete(patient);
    }

    @Override
    public List<PatientOutputDTO> getAll() {
        log.info("Service getAll patients");
        List<Patient>patients=patientRepo.findAll();
        if(!patients.isEmpty()){
            return patients.stream().map(patientMapper::toPatientOutputDTO).toList();
        }
        return null;
    }

    @Override
    public List<PatientOutputDTO> getPatientByName(String patientName) {
        log.info("Service getPatientByName patientName: {}", patientName);
        return patientRepo.findAllByPerson_FirstNameContainingIgnoreCaseOrPerson_LastNameContainingIgnoreCase(patientName,patientName).stream().map(patientMapper::toPatientOutputDTO).toList();
    }

    @Override
    public List<PatientOutputDTO> getFilteredPatients(Map<String, Object> filters) {
        log.info("Service getFilteredPatients filters: {}", filters);
        Specification<Patient> specification = patientSpecification.getPatientsByFilter(filters);
        List<Patient>patients=patientRepo.findAll(specification);
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
