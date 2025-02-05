package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MedicationInputDTO {

    @NotEmpty(message = "Message name cannot be empty")
    private String name;

    private boolean beforeMeal;
}
