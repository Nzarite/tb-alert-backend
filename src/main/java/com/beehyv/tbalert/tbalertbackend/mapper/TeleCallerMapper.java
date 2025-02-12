package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.entity.TeleCaller;
import com.beehyv.tbalert.tbalertbackend.repository.TeleCallerRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TeleCallerMapper {

    private final TeleCallerRepo teleCallerRepo;

    public TeleCaller find(Long id) {
        return teleCallerRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("TeleCaller not found for id: " + id));
    }
}
