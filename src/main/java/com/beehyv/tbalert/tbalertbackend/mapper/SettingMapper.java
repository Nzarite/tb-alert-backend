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
        return Setting.builder()
                .keyName(settingInputDTO.getKeyName())
                .value(settingInputDTO.getValue())
                .build();
    }

    public SettingOutputDTO toSettingOutputDTO(Setting setting) {
        return SettingOutputDTO.builder()
                .keyName(setting.getKeyName())
                .value(setting.getValue())
                .type(setting.getType())
                .category(setting.getCategory())
                .label(setting.getLabel())
                .placeholder(setting.getPlaceholder())
                .endpoint(setting.getEndpoint())
                .build();
    }
}
