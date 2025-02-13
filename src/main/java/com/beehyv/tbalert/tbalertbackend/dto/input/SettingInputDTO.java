package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettingInputDTO {
    @NotEmpty(message = "Key cannot be empty")
    private String key;

    @NotEmpty(message = "Value cannot be empty")
    private String value;

    @NotEmpty(message = "Type cannot be empty")
    private String type;

}

