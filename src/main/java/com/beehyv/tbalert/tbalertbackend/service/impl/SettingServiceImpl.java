package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.SettingInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.SettingOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Setting;
import com.beehyv.tbalert.tbalertbackend.mapper.SettingMapper;
import com.beehyv.tbalert.tbalertbackend.repository.SettingRepo;
import com.beehyv.tbalert.tbalertbackend.service.SettingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
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
                        .key(setting.getKeyName())
                        .value(setting.getValue())
                        .type(setting.getType())
                        .build())
                .toList();
    }

    @Override
    public void updateSetting(SettingInputDTO settingInputDTO) {
        Setting setting = settingRepo.findByKeyName(settingInputDTO.getKey());
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
        Setting setting = settingRepo.findByKeyName(key);
        if (setting != null) {
            settingRepo.delete(setting);
        }
        else{
            throw new IllegalArgumentException("Setting does not exist");
        }
    }
}
