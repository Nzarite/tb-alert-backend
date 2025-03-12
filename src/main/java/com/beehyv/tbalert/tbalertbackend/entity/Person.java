package com.beehyv.tbalert.tbalertbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String gender;

    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private String createdBy;

    private LocalDateTime createdOn;

    private String updatedBy;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private Boolean isDeleted=false;
}
