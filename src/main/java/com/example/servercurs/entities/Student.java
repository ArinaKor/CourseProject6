package com.example.servercurs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="id_user")
    private User id_user;
/*
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="id_group", nullable = false)
    @JsonIgnore
    private Group id_group;*/

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="id_group")
    @JsonIgnore
    private Group id_group;

    @Override
    public String toString() {
        return "Student{" +
                "id_student=" + id_student +
                ", payment='" + payment + '\'' +
                ", certificate='" + certificate + '\'' +
                ", rating=" + rating +
                ", id_user=" + id_user +
                '}';
    }
}
