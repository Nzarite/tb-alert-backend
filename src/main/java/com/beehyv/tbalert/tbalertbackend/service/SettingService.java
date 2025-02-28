package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.SettingInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.GroupedSettingsOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.SettingOutputDTO;

import java.util.List;

public interface SettingService {
    SettingOutputDTO addSetting(SettingInputDTO settingInputDTO);

    List<GroupedSettingsOutputDTO> getSettings();

    SettingOutputDTO getSetting(String keyName);

    List<SettingOutputDTO> getCategorySettings(String category);

    SettingOutputDTO updateSetting(SettingInputDTO settingInputDTO);

    void deleteSetting(String keyName);

    String getSettingsValue(String keyName);
}
