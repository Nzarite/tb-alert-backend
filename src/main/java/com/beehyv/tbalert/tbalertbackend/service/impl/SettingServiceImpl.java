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
    public SettingOutputDTO addSetting(SettingInputDTO settingInputDTO) {
        if(settingRepo.findByKeyName(settingInputDTO.getKeyName()) != null){
            throw new IllegalArgumentException("Setting already exists");
        }
        Setting setting = settingMapper.toSetting(settingInputDTO);
        settingRepo.save(setting);

        return settingMapper.toSettingOutputDTO(setting);
    }

    @Override
    public List<SettingOutputDTO> getSettings() {
        return settingRepo.findAll().stream()
                .map(setting -> SettingOutputDTO.builder()
                        .keyName(setting.getKeyName())
                        .value(setting.getValue())
                        .type(setting.getType())
                        .build())
                .toList();
    }

    @Override
    public SettingOutputDTO updateSetting(SettingInputDTO settingInputDTO) {
        Setting setting = settingRepo.findByKeyName(settingInputDTO.getKeyName());
        if (setting != null) {
            setting.setValue(settingInputDTO.getValue());
        }
        else{
            throw new IllegalArgumentException("Setting does not exist");
        }

        settingRepo.save(setting);

        return settingMapper.toSettingOutputDTO(setting);
    }

    @Override
    public void deleteSetting(String keyName) {
        Setting setting = settingRepo.findByKeyName(keyName);
        if (setting != null) {
            settingRepo.delete(setting);
        }
        else{
            throw new IllegalArgumentException("Setting does not exist");
        }
    }

    @Override
    public String getSettingsValue(String keyName) {
        Setting setting = settingRepo.findByKeyName(keyName);
        return setting != null ? setting.getValue() : null;
    }
}
