package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.ActivityTrackerInputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.ActivityTracker;
import jakarta.validation.Valid;

import java.util.List;

public interface ActivityTrackerService {
    String submit(@Valid ActivityTrackerInputDTO activityTrackerInputDTO);

    String deleteForm(Long id);

    ActivityTracker getForm(Long id);

    List<ActivityTracker> getAllForms();
}
