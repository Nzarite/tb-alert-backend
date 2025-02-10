package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.output.SettingsOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Settings;

import java.util.List;

public interface SettingsService {
    void addSetting(Settings setting);

    List<SettingsOutputDTO> getSettings();

    void updateSetting(Settings setting);

    void deleteSetting(String key);

}
