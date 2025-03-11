package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.GramPanchayatInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.GramPanchayatOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.GramPanchayat;
import com.beehyv.tbalert.tbalertbackend.mapper.GramPanchayatMapper;
import com.beehyv.tbalert.tbalertbackend.repository.GramPanchayatRepo;
import com.beehyv.tbalert.tbalertbackend.service.GramPanchayatService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class GramPanchayatImpl implements GramPanchayatService {

    private final GramPanchayatMapper gramPanchayatMapper;
    private GramPanchayatRepo gramPanchayatRepo;

    @Override
    public GramPanchayatOutputDTO addGramPanchayat(GramPanchayatInputDTO gramPanchayatInputDTO) {
        GramPanchayat gramPanchayat = gramPanchayatMapper.toGramPanchayat(gramPanchayatInputDTO);
        gramPanchayat=gramPanchayatRepo.save(gramPanchayat);
        return gramPanchayatMapper.gramPanchayatOutputDTO(gramPanchayat);
    }
}
