package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.SettingInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.SettingOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.SettingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setting")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
@PreAuthorize("hasAuthority('ROLE_SuperAdmin')")
public class SettingController {

    private final SettingService settingService;

    @GetMapping("/all")
    public ResponseEntity<List<SettingOutputDTO>> getSettings() {

        return new ResponseEntity<>(settingService.getSettings(), HttpStatus.OK);
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
