package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.TBDetailsInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.TBDetailsOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.TBDetails;
import com.beehyv.tbalert.tbalertbackend.mapper.LocalDateMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.TBDetailsMapper;
import com.beehyv.tbalert.tbalertbackend.repository.TBDetailsRepo;
import com.beehyv.tbalert.tbalertbackend.service.TBDetailsService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class TBDetailsServiceImpl implements TBDetailsService {

    private final TBDetailsRepo tbDetailsRepo;
    private final TBDetailsMapper tbDetailsMapper;
    private final LocalDateMapper localDateMapper;

    @Override
    public TBDetailsOutputDTO getTBDetails(Integer patientId) {

        log.info("inside getTBDetails");

        TBDetails tbDetails = tbDetailsRepo.findByPatient_Id(patientId).orElseThrow(() -> new IllegalArgumentException("" +
                "Patient with id " + patientId + " not found"));

        return tbDetailsMapper.toOutputDto(tbDetails);
    }

    @Override
    public TBDetailsOutputDTO registerTBDetails(TBDetailsInputDTO tbDetailsInputDTO) {

        log.info("inside registerTBDetails");

        TBDetails tbDetails = tbDetailsMapper.toTBDetails(tbDetailsInputDTO);
        tbDetailsRepo.save(tbDetails);
        return tbDetailsMapper.toOutputDto(tbDetails);
    }

    @Override
    public TBDetailsOutputDTO updateTBDetails(TBDetailsInputDTO tbDetailsInputDTO, Integer patientId) {

        log.info("inside updateTBDetails");

        TBDetails tbDetails = tbDetailsRepo.findByPatient_Id(patientId).orElseThrow(() -> new IllegalArgumentException("" +
                "Patient with id " + patientId + " not found"));

        tbDetails.setDateOfDiagnosis(localDateMapper.toLocalDate(tbDetailsInputDTO.getDateOfDiagnosis()));
        tbDetails.setDateOfTreatmentInitiation(localDateMapper.toLocalDate(tbDetailsInputDTO.getDateOfTreatmentInitiation()));
        tbDetails.setTypeOfPwtb(tbDetailsInputDTO.getTypeOfPwtb());
        tbDetails.setTypeOfTb(tbDetailsInputDTO.getTypeOfTb());
        tbDetails.setDstbOrDrtb(tbDetailsInputDTO.getDstbOrDrtb());
        TBDetails updatedTBDetails = tbDetailsRepo.save(tbDetails);

        return tbDetailsMapper.toOutputDto(updatedTBDetails);
    }

    @Override
    public void deleteTBDetails(Integer patientId) {

        log.info("inside deleteTBDetails");

        TBDetails tbDetails = tbDetailsRepo.findByPatient_Id(patientId).orElseThrow(() -> new IllegalArgumentException("" +
                "Patient with id " + patientId + " not found"));

        tbDetailsRepo.delete(tbDetails);
    }

}
