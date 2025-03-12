package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.GramPanchayatInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.GramPanchayatOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.GramPanchayat;
import com.beehyv.tbalert.tbalertbackend.mapper.GramPanchayatMapper;
import com.beehyv.tbalert.tbalertbackend.repository.GramPanchayatRepo;
import com.beehyv.tbalert.tbalertbackend.service.GramPanchayatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class GramPanchayatImpl implements GramPanchayatService {

    private final GramPanchayatMapper gramPanchayatMapper;
    private final GramPanchayatRepo gramPanchayatRepo;

    @Override
    public GramPanchayatOutputDTO addGramPanchayat(GramPanchayatInputDTO gramPanchayatInputDTO) {
        GramPanchayat gramPanchayat = gramPanchayatMapper.toGramPanchayat(gramPanchayatInputDTO);
        gramPanchayat=gramPanchayatRepo.save(gramPanchayat);
        return gramPanchayatMapper.gramPanchayatOutputDTO(gramPanchayat);
    }

    @Override
    public List<GramPanchayatOutputDTO> getAll() {
        return gramPanchayatRepo.findAll().stream().map(gramPanchayatMapper::gramPanchayatOutputDTO).toList();
    }

    @Override
    public List<GramPanchayatOutputDTO> getAllByMandalId(Long id) {
        return gramPanchayatRepo.findAllByMandal_Id(id).stream().map(gramPanchayatMapper::gramPanchayatOutputDTO).toList();
    }

    @Override
    public GramPanchayatOutputDTO getById(Long id) {
        return gramPanchayatMapper.gramPanchayatOutputDTO(gramPanchayatMapper.getGramPanchayatById(id));
    }
}
