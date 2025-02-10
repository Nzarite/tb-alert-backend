package com.beehyv.tbalert.tbalertbackend.dto.output;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SettingsOutputDTO {
    private String key;
    private List<String> value;
}
