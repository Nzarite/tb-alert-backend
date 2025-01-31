package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.NikshayInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.NikshayOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.NikshayMitra;
import com.beehyv.tbalert.tbalertbackend.mapper.NikshayMapper;
import com.beehyv.tbalert.tbalertbackend.repository.NikshayMitraRepo;
import com.beehyv.tbalert.tbalertbackend.service.NikshayMitraService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NikshayMitraServiceImpl implements NikshayMitraService {

    private final NikshayMapper nikshayMapper;
    private final NikshayMitraRepo nikshayMitraRepo;

    @Transactional
    @Override
    public NikshayOutputDTO registerNikshayDetails(NikshayInputDTO nikshayInputDTO) {
        NikshayMitra nikshayMitra = nikshayMapper.ToEntity(nikshayInputDTO);
        nikshayMitraRepo.save(nikshayMitra);
        return nikshayMapper.ToOutputDto(nikshayMitra);
    }

    @Transactional
    @Override
    public NikshayOutputDTO updateNikshayDetails(int patientId, NikshayInputDTO nikshayInputDTO) {
        NikshayMitra nikshayMitra = nikshayMitraRepo.findByPatient_Id(patientId).orElseThrow(()-> new IllegalArgumentException("" +
                "Patient with id " + patientId + " not found"));

        // incomplete update service
        return null;
    }
}
