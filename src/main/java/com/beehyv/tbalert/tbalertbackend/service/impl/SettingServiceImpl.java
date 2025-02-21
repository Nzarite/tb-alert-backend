package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.SettingInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.SettingOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Setting;
import com.beehyv.tbalert.tbalertbackend.mapper.SettingMapper;
import com.beehyv.tbalert.tbalertbackend.repository.SettingRepo;
import com.beehyv.tbalert.tbalertbackend.service.SettingService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class SettingServiceImpl implements SettingService {
    private SettingRepo settingRepo;
    private SettingMapper settingMapper;

    @Override
    public void addSetting(SettingInputDTO settingInputDTO) {
        Setting setting = settingMapper.toSetting(settingInputDTO);
        settingRepo.save(setting);
    }

    @Override
    public List<SettingOutputDTO> getSettings() {
        return settingRepo.findAll().stream()
                .map(setting -> SettingOutputDTO.builder()
                        .key(setting.getKey())
                        .value(setting.getValue())
                        .type(setting.getType())
                        .build())
                .toList();
    }

    @Override
    public void updateSetting(SettingInputDTO settingInputDTO) {
        Setting setting = settingRepo.findByKey(settingInputDTO.getKey());
        if (setting != null) {
            setting.setValue(settingInputDTO.getValue());
        }
        else{
            throw new IllegalArgumentException("Setting does not exist");
        }

        settingRepo.save(setting);
    }

    @Override
    public void deleteSetting(String key) {
        Setting setting = settingRepo.findByKey(key);
        if (setting != null) {
            settingRepo.delete(setting);
        }
        else{
            throw new IllegalArgumentException("Setting does not exist");
        }
    }
}
