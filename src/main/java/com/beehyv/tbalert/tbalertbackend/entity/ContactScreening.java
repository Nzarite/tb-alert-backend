package com.beehyv.tbalert.tbalertbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contact_screening")
public class ContactScreening {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "contact_screening_status", nullable = false)
    private Boolean contactScreeningDone;

    @Column(name = "date_of_contact_screening")
    private LocalDate dateOfContactScreening;

    @Column(name = "hhcs_available")
    private Integer noOfHHCsAvailable;

    @Column(name = "hhcs_screened")
    private Integer noOfHHCsScreened;

    @Column(name = "hhcs_with_tb_symptoms")
    private Integer noOfHHCsWithTBSymptoms;

    @Column(name = "hhcs_referred_tb_testing")
    private Integer noOfHHCsReferredTBTesting;

    @Column(name = "hhcs_diagnosed_tb")
    private Integer noOfHHCsDiagnosedTB;

    @Column(name = "hhcs_tb_initiated_att")
    private Integer noOfHHCsTBInitiatedATT;

    @Column(name = "hhcs_ltbi_test")
    private Integer noOfHHCsUndergoneLTBITest;

    @Column(name = "eligible_for_tpt")
    private Integer noOfEligibleForTPT;

    @Column(name = "hhcs_initiated_tpt")
    private Integer noOfHHCsInitiatedTPT;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
}
