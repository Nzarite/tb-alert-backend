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
@Table(name = "tb_details")
public class TBDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "date_of_diagnosis")
    private LocalDate dateOfDiagnosis;

    @Column(nullable = false, name = "date_of_treatment_initiation")
    private LocalDate dateOfTreatmentInitiation;

    @Column(nullable = false, name = "type_of_pwtb")
    private String typeOfPwtb;

    @Column(nullable = false, name = "type_of_tb")
    private String typeOfTb;

    @Column(nullable = false, name = "dstb_drtb")
    private String dstbOrDrtb;

    @Column(nullable = false, name = "clinical_microbiological")
    private String clinicalOrMicrobiological;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
}
