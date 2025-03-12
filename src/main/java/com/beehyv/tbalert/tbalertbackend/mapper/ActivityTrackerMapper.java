package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.ActivityTrackerInputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.ActivityData;
import com.beehyv.tbalert.tbalertbackend.entity.ActivityTracker;
import com.beehyv.tbalert.tbalertbackend.entity.Address;
import com.beehyv.tbalert.tbalertbackend.entity.GramPanchayat;
import com.beehyv.tbalert.tbalertbackend.repository.ActivityTrackerRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ActivityTrackerMapper {

    private LocalDateMapper localDateMapper;
    private GramPanchayatMapper gramPanchayatMapper;
    private ActivityTrackerRepo activityTrackerRepo;

    public ActivityTracker findActivityTracker(Long id) {
        return activityTrackerRepo.findById(id).filter(activityTracker -> !activityTracker.isDeleted()).orElseThrow(() -> new IllegalArgumentException("Form with id: " + id + " not found"));
    }

    public ActivityTracker toActivityTracker(ActivityTrackerInputDTO activityTrackerInputDTO) {
        // Activity Data List
        List<ActivityData> activityData = toActivityDataList(activityTrackerInputDTO);

        // Address
        GramPanchayat gramPanchayat = gramPanchayatMapper.getGramPanchayatById(activityTrackerInputDTO.getGramPanchayat());
        Address address = Address.builder()
                .village(activityTrackerInputDTO.getVillage())
                .gramPanchayat(gramPanchayat)
                .build();

        return ActivityTracker.builder()
                .activityData(activityData)
                .dateOfActivity(localDateMapper.toLocalDate(activityTrackerInputDTO.getDateOfActivity()))
                .createdBy(activityTrackerInputDTO.getCreatedBy())
                .stakeholderEmail(activityTrackerInputDTO.getStakeholderEmail())
                .address(address)
                .build();
    }

    private List<ActivityData> toActivityDataList(ActivityTrackerInputDTO activityTrackerInputDTO) {
        List<ActivityData> activityData = new ArrayList<>();
        activityTrackerInputDTO.getActivities().forEach(activity -> {
            ActivityData activityData1 = new ActivityData();

            activityData1.setActivityType(activity.getActivityType());
            activityData1.setNoOfMales(activity.getNoOfMales());
            activityData1.setNoOfFemales(activity.getNoOfFemales());
            activityData1.setCountOfPeople(activity.getNoOfFemales() + activity.getNoOfMales());
            activityData1.setActivityType(activity.getActivityType());

            if (activity.getActivityType().equals("Supported/Conducted TB awareness meeting")) {
                activityData1.setNoOfAwarenessCampsConducted(activity.getNoOfAwarenessCampsConducted());
            } else if (activity.getActivityType().equals("Organized/supported TB awareness meeting")) {
                activityData1.setNoOfXRayCampsConducted(activity.getNoOfXRayCampsConducted());
            }
        });
        return activityData;
    }
}
