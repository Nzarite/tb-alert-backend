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
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, name = "block")
    private String block;

    @Column(nullable = false, name = "gp")
    private String gp;

    @Column(nullable = false, name = "village")
    private String village;

    @Column(nullable = false, name = "district")
    private String district;

    @ManyToOne(cascade = CascadeType.ALL)
    private State state;
}
