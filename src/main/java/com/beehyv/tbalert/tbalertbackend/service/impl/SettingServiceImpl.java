package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.SettingInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.GroupedSettingsOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.SettingOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Setting;
import com.beehyv.tbalert.tbalertbackend.mapper.MedicationMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.SettingMapper;
import com.beehyv.tbalert.tbalertbackend.repository.SettingRepo;
import com.beehyv.tbalert.tbalertbackend.service.SettingService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Transactional
public class SettingServiceImpl implements SettingService {
    private final MedicationMapper medicationMapper;
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
                    String keyName = settingOutputDTO.getKeyName();
                    if(keyName.equals("dstb_medicines") || keyName.equals("drtb_medicines")){
                        String value = settingOutputDTO.getValue();
                        settingOutputDTO.setValue(convertMedicationIdsToNames(value));
                    }
                    String category = setting.getCategory();
                    if(!mappedSettings.containsKey(category)){
                        mappedSettings.put(category, new ArrayList<>());
                    }
                    mappedSettings.get(category).add(settingOutputDTO);
                });
        List<GroupedSettingsOutputDTO> groupedSettings = new ArrayList<>();

        List<String> categories = Arrays.asList("General", "SMS", "Followup", "Medication", "State");
        for(String category: categories){
            List<SettingOutputDTO> categorySettings = mappedSettings.get(category);
            groupedSettings.add(GroupedSettingsOutputDTO.builder()
                            .category(category)
                            .settings(categorySettings)
                            .build());
        }

        return groupedSettings;
    }

    @Override
    public SettingOutputDTO getSetting(String keyName) {
        Setting setting = settingRepo.findByKeyName(keyName);
        if(setting == null){
            throw new IllegalArgumentException("Setting does not exist");
        }
        SettingOutputDTO settingOutputDTO = settingMapper.toSettingOutputDTO(setting);

        if(keyName.equals("dstb_medicines") || keyName.equals("drtb_medicines")){
            String value = settingOutputDTO.getValue();
            settingOutputDTO.setValue(convertMedicationIdsToNames(value));
        }


        return settingOutputDTO;
    }

    @Override
    public List<SettingOutputDTO> getCategorySettings(String category) {
        return settingRepo.findAll().stream()
                .filter(setting -> setting.getCategory().equals(category))
                .map(setting -> {
                    SettingOutputDTO settingOutputDTO = settingMapper.toSettingOutputDTO(setting);
                    if(category.equals("Medication")){
                        String value = settingOutputDTO.getValue();
                        settingOutputDTO.setValue(convertMedicationIdsToNames(value));
                    }
                    return settingOutputDTO;
                })
                .toList();
    }

    @Override
    public SettingOutputDTO updateSetting(SettingInputDTO settingInputDTO) {
        Setting setting = settingRepo.findByKeyName(settingInputDTO.getKeyName());
        String value = settingInputDTO.getValue();
        if (setting != null) {
            String keyName = settingInputDTO.getKeyName();
            value = switch (keyName) {
                case "dstb_medicines", "drtb_medicines" -> convertMedicationNamesToIds(settingInputDTO.getValue());
                default -> value;
            };
            setting.setValue(value);
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

    @Override
    public String convertMedicationIdsToNames(String value) {
        List<String> ids = List.of(value.split(","));
        List<String> names = new ArrayList<>();
        ids.forEach(id -> {
            names.add(medicationMapper.idToName(Integer.parseInt(id)));
        });

        return String.join(",", names);
    }

    @Override
    public String convertMedicationNamesToIds(String value) {
        List<String> names = List.of(value.split(","));
        List<String> ids = new ArrayList<>();
        names.forEach(name -> {
            ids.add(medicationMapper.nameToId(name));
        });

        return String.join(",", ids);
    }
}
