package com.beehyv.tbalert.tbalertbackend.repository;

import com.beehyv.tbalert.tbalertbackend.entity.ActivityTracker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityTrackerRepo extends JpaRepository<ActivityTracker, Long> {
}
