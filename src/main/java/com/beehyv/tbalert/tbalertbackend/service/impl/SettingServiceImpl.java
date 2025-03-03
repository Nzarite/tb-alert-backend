package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.SettingInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.SettingOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.GroupedSettingsOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Setting;
import com.beehyv.tbalert.tbalertbackend.mapper.SettingMapper;
import com.beehyv.tbalert.tbalertbackend.repository.SettingRepo;
import com.beehyv.tbalert.tbalertbackend.service.SettingService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<GroupedSettingsOutputDTO> getSettings() {
        Map <String, List<SettingOutputDTO>> mappedSettings = new HashMap<>();
        settingRepo.findAll()
                .forEach(setting -> {
                    SettingOutputDTO settingOutputDTO = settingMapper.toSettingOutputDTO(setting);
                    String category = setting.getCategory();
                    if(!mappedSettings.containsKey(category)){
                        mappedSettings.put(category, new ArrayList<>());
                    }
                    mappedSettings.get(category).add(settingOutputDTO);
                });
        List<GroupedSettingsOutputDTO> groupedSettings = new ArrayList<>();
        mappedSettings.forEach((category, settings) -> {
            groupedSettings.add(GroupedSettingsOutputDTO.builder()
                    .category(category)
                    .settings(settings)
                    .build());
        }
        );

        return groupedSettings;
    }

    @Override
    public SettingOutputDTO getSetting(String keyName) {
        Setting setting = settingRepo.findByKeyName(keyName);
        if(setting == null){
            throw new IllegalArgumentException("Setting does not exist");
        }

        return settingMapper.toSettingOutputDTO(setting);
    }

    @Override
    public List<SettingOutputDTO> getCategorySettings(String category) {
        return settingRepo.findAll().stream()
                .filter(setting -> setting.getCategory().equals(category))
                .map(setting -> settingMapper.toSettingOutputDTO(setting))
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
