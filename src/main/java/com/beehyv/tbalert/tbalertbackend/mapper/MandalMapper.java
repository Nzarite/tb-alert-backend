package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.MandalInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MandalOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.District;
import com.beehyv.tbalert.tbalertbackend.entity.Mandal;
import com.beehyv.tbalert.tbalertbackend.repository.MandalRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
@Slf4j
public class MandalMapper {

    private final DistrictMapper districtMapper;
    private final MandalRepo mandalRepo;

    public Mandal getMandalById(Long id) {
        return mandalRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Mandal not found"));
    }

    public MandalOutputDTO toMandalOutputDTO(Mandal mandal) {
        return MandalOutputDTO
                            .builder()
                            .id(mandal.getId())
                            .district(districtMapper.toDistrictOutputDTO(mandal.getDistrict()))
                            .name(mandal.getName())
                            .build();
    }

    public Mandal toMandal(MandalInputDTO mandalInputDTO) {
        District district = districtMapper.findDistrictById(mandalInputDTO.getDistrictId());

        return Mandal.builder()
                .name(mandalInputDTO.getMandalName())
                .district(district)
                .build();
    }
}
