package com.beehyv.tbalert.tbalertbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "nikshay_mitra")
public class NikshayMitra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "nikshay_id")
    private String nikshayId;

    @Column(nullable = false, name = "udst_status")
    private Boolean udstStatus;

    @Column(name = "date_of_udst")
    private LocalDate dateOfUdst;

    @Column(name = "result_of_udst")
    private String resultOfUdst;

    @Column(nullable = false, name = "dbt_status")
    private Boolean dbtStatus;

    @Column(name = "date_of_dbt")
    private LocalDate dateOfDbt;

    @Column(nullable = false, name = "nikshay_mitra_status")
    private Boolean nikshayMitraStatus;

    @Column(name = "nikshay_mitra_date")
    private LocalDate nikshayMitraDate;

    @Column(name = "nikshay_mitra_name")
    private String nikshayMitraName;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
}
