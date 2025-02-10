package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.output.SettingsOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Settings;
import com.beehyv.tbalert.tbalertbackend.service.SettingsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/settings")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping("/all")
    public ResponseEntity<List<SettingsOutputDTO>> getSettings() {
        return new ResponseEntity<>(settingsService.getSettings(), HttpStatus.OK);
    }

    @PostMapping("/add/{key}")
    public ResponseEntity<?> addSetting(@PathVariable String key, @RequestBody List<String> value) {
        settingsService.addSetting(Settings.builder()
                .key(key)
                .value(String.join(",", value))
                .build());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{key}")
    public ResponseEntity<?> updateSetting(@PathVariable String key, @RequestBody List<String> value) {
        settingsService.updateSetting(Settings.builder()
                .key(key)
                .value(String.join(",", value))
                .build());

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{key}")
    public ResponseEntity<?> deleteSetting(@PathVariable String key) {
        settingsService.deleteSetting(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
