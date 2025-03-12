package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.DistrictInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.DistrictOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.District;
import com.beehyv.tbalert.tbalertbackend.entity.State;
import com.beehyv.tbalert.tbalertbackend.repository.DistrictRepo;
import com.beehyv.tbalert.tbalertbackend.repository.StateRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class DistrictMapper {

    private final StateMapper stateMapper;
    private final StateRepo stateRepo;
    private final DistrictRepo districtRepo;

    public District findDistrictById(Long id) {
        return districtRepo.findById(id).orElseThrow(()->new IllegalArgumentException("District not found"));
    }

    public DistrictOutputDTO toDistrictOutputDTO(District district) {
        log.info("Mapper called toDistrictOutputDTO");
        return DistrictOutputDTO
                .builder()
                .id(district.getId())
                .name(district.getName())
                .state(stateMapper.toStateOutputDTO(district.getState()))
                .build();
    }

    public District toDistrict(DistrictInputDTO districtInputDTO) {
        log.info("Mapper called toDistrict");
        State state=stateMapper.getStateByName(districtInputDTO.getStateName());
        return District
                      .builder()
                      .state(state)
                      .name(districtInputDTO.getDistrictName())
                      .build();
    }
}
