package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.GramPanchayatInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.GramPanchayatOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.GramPanchayat;
import com.beehyv.tbalert.tbalertbackend.entity.Mandal;
import com.beehyv.tbalert.tbalertbackend.repository.GramPanchayatRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class GramPanchayatMapper {

    private final MandalMapper mandalMapper;
    private final GramPanchayatRepo gramPanchayatRepo;

    public GramPanchayat getGramPanchayatById(Long id)
    {
        return gramPanchayatRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Gram panchayat not found"));
    }

    public GramPanchayat toGramPanchayat(GramPanchayatInputDTO gramPanchayatInputDTO) {
        Mandal mandal = mandalMapper.getMandalById(gramPanchayatInputDTO.getMandalId());
        return GramPanchayat
                            .builder()
                            .mandal(mandal)
                            .name(gramPanchayatInputDTO.getGramPanchayatName())
                            .build();
    }

    public GramPanchayatOutputDTO gramPanchayatOutputDTO(GramPanchayat gramPanchayat) {
        return GramPanchayatOutputDTO
                                    .builder()
                                    .id(gramPanchayat.getId())
                                    .mandal(mandalMapper.toMandalOutputDTO(gramPanchayat.getMandal()))
                                    .name(gramPanchayat.getName())
                                    .build();
    }
}
