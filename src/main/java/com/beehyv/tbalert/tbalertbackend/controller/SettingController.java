package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.SettingInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.SettingOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.GroupedSettingsOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.SettingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/setting")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
@PreAuthorize("hasAuthority('ROLE_SuperAdmin')")
public class SettingController {

    private final SettingService settingService;

    @GetMapping("/all")
    public ResponseEntity<List<GroupedSettingsOutputDTO>> getSettings() {

        return new ResponseEntity<>(settingService.getSettings(), HttpStatus.OK);
    }

    @GetMapping("/key/{keyName}")
    public ResponseEntity<SettingOutputDTO> getSetting(@PathVariable String keyName) {
        return new ResponseEntity<>(settingService.getSetting(keyName), HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<SettingOutputDTO>> getCategorySettings(@PathVariable String category) {
        return new ResponseEntity<>(settingService.getCategorySettings(category), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SettingOutputDTO> addSetting(@Valid @RequestBody SettingInputDTO settingInputDTO) {
        return new ResponseEntity<>(settingService.addSetting(settingInputDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<SettingOutputDTO> updateSetting(@Valid @RequestBody SettingInputDTO settingInputDTO) {
        return new ResponseEntity<>(settingService.updateSetting(settingInputDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{keyName}")
    public ResponseEntity<HttpStatus> deleteSetting(@PathVariable String keyName) {
        settingService.deleteSetting(keyName);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
