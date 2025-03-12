package com.beehyv.tbalert.tbalertbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "activity_data")
public class ActivityData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String activityType;

    @Column(nullable = false)
    private int noOfMales;

    @Column(nullable = false)
    private int noOfFemales;

    @Column(nullable = false)
    private int countOfPeople;

    private int noOfAwarenessCampsConducted;

    private int noOfXRayCampsConducted;

}
