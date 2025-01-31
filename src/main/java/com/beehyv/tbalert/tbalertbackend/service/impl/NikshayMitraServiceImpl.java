package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.NikshayMitraDTO;
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
    public NikshayMitraDTO registerNikshayDetails(NikshayMitraDTO nikshayMitraDTO) {
        NikshayMitra nikshayMitra = nikshayMapper.DtoToEntity(nikshayMitraDTO);
        nikshayMitraRepo.save(nikshayMitra);
        return nikshayMapper.EntityToDto(nikshayMitra);
    }
}
