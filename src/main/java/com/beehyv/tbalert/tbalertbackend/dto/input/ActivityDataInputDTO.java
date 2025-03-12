package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivityDataInputDTO {

    @NotEmpty(message = "Activity type is required")
    private String activityType;

    private String activityName;

    @NotNull(message = "Count of males is required")
    private int noOfMales;

    @NotNull(message = "Count of females is required")
    private int noOfFemales;

    private int noOfAwarenessCampsConducted;

    private int noOfXRayCampsConducted;
}
