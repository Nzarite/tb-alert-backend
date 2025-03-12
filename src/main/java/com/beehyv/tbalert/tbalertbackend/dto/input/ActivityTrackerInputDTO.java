package com.beehyv.tbalert.tbalertbackend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ActivityTrackerInputDTO {

    @NotEmpty(message = "Stakeholder Email is required")
    private String stakeholderEmail;

    @NotNull(message = "State is required")
    private Long state;

    @NotNull(message = "District is required")
    private Long district;

    @NotNull(message = "Mandal Taluka is required")
    private Long mandal;

    @NotNull(message = "Gram Panchayat is required")
    private Long gramPanchayat;

    @NotEmpty(message = "Village is required")
    private String village;

    @NotEmpty(message = "Date of Activity is required")
    private String dateOfActivity;

    @NotNull(message = "Activities is required")
    private List<ActivityDataInputDTO> activities;

    @NotEmpty(message = "Created By is required")
    private String createdBy;
}
