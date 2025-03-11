package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.DistrictInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.DistrictOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.District;
import com.beehyv.tbalert.tbalertbackend.entity.State;
import com.beehyv.tbalert.tbalertbackend.mapper.DistrictMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.StateMapper;
import com.beehyv.tbalert.tbalertbackend.repository.DistrictRepo;
import com.beehyv.tbalert.tbalertbackend.service.DistrictService;
import com.beehyv.tbalert.tbalertbackend.service.StateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepo districtRepo;
    private final DistrictMapper districtMapper;

    @Override
    public DistrictOutputDTO addDistrict(DistrictInputDTO districtInputDTO) {
        District district=districtMapper.toDistrict(districtInputDTO);
        log.info("District added using Service: {}", district);
        district=districtRepo.save(district);
        return districtMapper.toDistrictOutputDTO(district);
    }

}
