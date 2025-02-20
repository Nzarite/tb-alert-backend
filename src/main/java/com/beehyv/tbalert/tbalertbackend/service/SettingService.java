package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.SettingInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.SettingOutputDTO;

import java.util.List;

public interface SettingService {
    SettingOutputDTO addSetting(SettingInputDTO settingInputDTO);

    List<SettingOutputDTO> getSettings();

    SettingOutputDTO updateSetting(SettingInputDTO settingInputDTO);

    void deleteSetting(String keyName);

}
