package com.example.servercurs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_student;
    @Column(name="payment")
    private String payment;

    @Column(name="certificate")
    private String certificate;

    @Column(name="average_rating")
    private double rating;
    //without foreight keys
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_user")
    private User id_user;
/*
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="id_group", nullable = false)
    @JsonIgnore
    private Group id_group;*/
}
