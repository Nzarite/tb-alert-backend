package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.MandalInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MandalOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Mandal;
import com.beehyv.tbalert.tbalertbackend.mapper.MandalMapper;
import com.beehyv.tbalert.tbalertbackend.repository.MandalRepo;
import com.beehyv.tbalert.tbalertbackend.service.MandalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MandalServiceImpl implements MandalService {

    private final MandalRepo mandalRepo;
    private final MandalMapper mandalMapper;

    @Override
    public MandalOutputDTO addMandal(MandalInputDTO mandalInputDTO) {

        Mandal mandal=mandalMapper.toMandal(mandalInputDTO);
        mandal=mandalRepo.save(mandal);
        return mandalMapper.toMandalOutputDTO(mandal);
    }
}
