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
@Table(name = "patient")
public class Patient {

    @Id
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;

    private String currentStatus="alive";

    private boolean cured=false;

    private int age;

    private Boolean consentForMessage=false;
}
