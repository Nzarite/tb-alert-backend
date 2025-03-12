package com.beehyv.tbalert.tbalertbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medication")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",nullable = false,unique = true)
    private String name;

    @Column(name = "beforeMeal", nullable = false)
    private boolean beforeMeal;

}
