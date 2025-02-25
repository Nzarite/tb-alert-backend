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

    @Column(name = "block")
    private String block;

    @Column(name = "gp")
    private String gp;

    @Column(name = "village")
    private String village;

    @Column(name = "district")
    private String district;

    @ManyToOne(cascade = CascadeType.ALL)
    private State state;
}
