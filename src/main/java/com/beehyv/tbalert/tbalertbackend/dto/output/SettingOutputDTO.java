package com.beehyv.tbalert.tbalertbackend.dto.output;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettingOutputDTO {
    private String key;
    private String value;
    private String type;
}
