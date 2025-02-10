package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.output.SettingsOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Settings;
import com.beehyv.tbalert.tbalertbackend.repository.SettingsRepo;
import com.beehyv.tbalert.tbalertbackend.service.SettingsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class SettingsServiceImpl implements SettingsService {
    private SettingsRepo settingsRepo;

    @Override
    public void addSetting(Settings setting) {
        settingsRepo.save(setting);
    }

    @Override
    public List<SettingsOutputDTO> getSettings() {
        return settingsRepo.findAll().stream()
                .map(setting -> SettingsOutputDTO.builder()
                        .key(setting.getKey())
                        .value(Arrays.asList(setting.getValue().split(",")))
                        .build())
                .toList();
    }

    @Override
    public void updateSetting(Settings setting) {
        settingsRepo.save(setting);
    }

    @Override
    public void deleteSetting(String key) {
        Settings setting = settingsRepo.findById(key).orElseThrow(() -> new IllegalArgumentException("No such setting exists"));
        settingsRepo.delete(setting);
    }
}
