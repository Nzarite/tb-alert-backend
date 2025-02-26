package com.beehyv.tbalert.tbalertbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "state")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, name = "state_name", unique = true)
    private String stateName;

    @Column(nullable = false, name = "state_code", unique = true)
    private String stateCode;
}
