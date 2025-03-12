package com.beehyv.tbalert.tbalertbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "activity_tracker")
public class ActivityTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "activity_tracker_id")
    private List<ActivityData> activityData;

    private LocalDate dateOfActivity;

    private String createdBy;

    private String stakeholderEmail;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private boolean isDeleted = false;
}
