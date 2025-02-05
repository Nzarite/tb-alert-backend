package com.beehyv.tbalert.tbalertbackend.dto.output;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicationOutputDTO {

    private int id;

    private String name;

    private boolean beforeMeal;
}
