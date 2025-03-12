package com.beehyv.tbalert.tbalertbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicationReminder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reminderId;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "medication_id")
    private Medication medication;

    private LocalTime medicationTime;

    private LocalDate medicationDeadline;

    private String notificationStatus = "PENDING";
}
