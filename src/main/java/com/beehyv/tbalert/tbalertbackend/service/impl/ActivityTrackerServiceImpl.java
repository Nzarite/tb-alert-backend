package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.ActivityTrackerInputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.ActivityTracker;
import com.beehyv.tbalert.tbalertbackend.mapper.ActivityTrackerMapper;
import com.beehyv.tbalert.tbalertbackend.repository.ActivityTrackerRepo;
import com.beehyv.tbalert.tbalertbackend.service.ActivityTrackerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ActivityTrackerServiceImpl implements ActivityTrackerService {

    private final ActivityTrackerRepo activityTrackerRepo;
    private final ActivityTrackerMapper activityTrackerMapper;

    @Override
    public String submit(ActivityTrackerInputDTO activityTrackerInputDTO) {
        ActivityTracker activityTracker = activityTrackerMapper.toActivityTracker(activityTrackerInputDTO);
        activityTrackerRepo.save(activityTracker);
        return "Submitted successfully";
    }

    @Override
    public String deleteForm(Long id) {
        ActivityTracker activityTracker = activityTrackerMapper.findActivityTracker(id);
        activityTracker.setDeleted(true);
        activityTrackerRepo.save(activityTracker);
        return "Form Deleted Successfully";
    }

    @Override
    public ActivityTracker getForm(Long id) {
        return activityTrackerMapper.findActivityTracker(id);
    }

    @Override
    public List<ActivityTracker> getAllForms() {
        return activityTrackerRepo.findAll();
    }
}
