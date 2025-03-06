package com.beehyv.tbalert.tbalertbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient")
public class Patient {

    @Id
    private String id;

    @OneToOne
    private Person person;

    private String currentStatus="alive";

    private boolean cured=false;

    private int age;

    private Boolean consentForMessage=false;

    private String reminderTime;
}
