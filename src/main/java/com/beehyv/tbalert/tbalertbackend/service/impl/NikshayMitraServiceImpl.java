package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.NikshayInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.NikshayOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.NikshayMitra;
import com.beehyv.tbalert.tbalertbackend.mapper.LocalDateMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.NikshayMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMapper;
import com.beehyv.tbalert.tbalertbackend.repository.NikshayMitraRepo;
import com.beehyv.tbalert.tbalertbackend.service.NikshayMitraService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class NikshayMitraServiceImpl implements NikshayMitraService {

    private final NikshayMapper nikshayMapper;
    private final NikshayMitraRepo nikshayMitraRepo;
    private final PatientMapper patientMapper;
    private final LocalDateMapper localDateMapper;

    @Override
    public NikshayOutputDTO getNikshayDetails(String patientId) {
        log.info("inside getNikshayDetails");

        NikshayMitra nikshayMitra = nikshayMitraRepo.findByPatient_Id(patientId).orElseThrow(() -> new IllegalArgumentException("Patient with id " + patientId + " not found"));
        return nikshayMapper.ToOutputDto(nikshayMitra);
    }

    @Override
    public NikshayOutputDTO registerNikshayDetails(NikshayInputDTO nikshayInputDTO) {
        log.info("inside registerNikshayDetails");

        NikshayMitra nikshayMitra = nikshayMapper.ToEntity(nikshayInputDTO);
        nikshayMitraRepo.save(nikshayMitra);
        return nikshayMapper.ToOutputDto(nikshayMitra);
    }

    @Override
    public NikshayOutputDTO updateNikshayDetails(String patientId, NikshayInputDTO nikshayInputDTO) {
        log.info("inside updateNikshayDetails");

        NikshayMitra nikshayMitra = nikshayMitraRepo.findByPatient_Id(patientId).orElseThrow(() -> new IllegalArgumentException("Patient with id " + patientId + " not found"));
        nikshayMitra.setNikshayId(nikshayInputDTO.getNikshayId());
        nikshayMitra.setUdstStatus(nikshayInputDTO.getUdstStatus());
        nikshayMitra.setDateOfUdst(localDateMapper.toLocalDate(nikshayInputDTO.getDateOfUdst()));
        nikshayMitra.setResultOfUdst(nikshayInputDTO.getResultOfUdst());
        nikshayMitra.setDbtStatus(nikshayInputDTO.getDbtStatus());
        nikshayMitra.setDateOfDbt(localDateMapper.toLocalDate(nikshayInputDTO.getDateOfDbt()));
        nikshayMitra.setNikshayMitraStatus(nikshayInputDTO.getNikshayMitraStatus());
        nikshayMitra.setNikshayMitraDate(localDateMapper.toLocalDate(nikshayInputDTO.getNikshayMitraDate()));
        nikshayMitra.setNikshayMitraName(nikshayInputDTO.getNikshayMitraName());
        NikshayMitra updatedNikshayMitra = nikshayMitraRepo.save(nikshayMitra);
        nikshayMitraRepo.save(updatedNikshayMitra);

        return nikshayMapper.ToOutputDto(updatedNikshayMitra);
    }

    @Override
    public void deleteNikshayDetails(String patientId) {
        log.info("inside deleteNikshayDetails");

        Optional<NikshayMitra> nikshayMitra = nikshayMitraRepo.findByPatient_Id(patientId);
        nikshayMitra.ifPresent(nikshayMitraRepo::delete);
    }
}
