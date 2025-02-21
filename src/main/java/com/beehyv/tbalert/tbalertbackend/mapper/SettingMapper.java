package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.SettingInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.SettingOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Setting;
import com.beehyv.tbalert.tbalertbackend.repository.SettingRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SettingMapper {
    private SettingRepo settingRepo;

    public Setting toSetting(SettingInputDTO settingInputDTO) {
        Setting setting = settingRepo.findByKeyName(settingInputDTO.getKey());
        if (setting == null) {
            throw new IllegalArgumentException("Invalid key: " + settingInputDTO.getKey() + " already exists");
        }
        return setting;
    }

    public SettingOutputDTO toSettingOutputDTO(Setting setting) {
        return SettingOutputDTO.builder()
                .key(setting.getKeyName())
                .value(setting.getValue())
                .type(setting.getType())
                .build();
    }
}
