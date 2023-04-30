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

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_student;
    @Column(name="balance")
    private double balance;

    @Column(name="certificate")
    private String certificate;

    @Column(name="average_rating")
    private double rating;
    @Column(name="courses")
    private String courses;
    //without foreight keys
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="id_user")
    private User id_user;
/*
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="id_group", nullable = false)
    @JsonIgnore
    private Group id_group;*/

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name="id_group")
    @JsonIgnore
    private Group id_group;

    @Override
    public String toString() {
        return "Student{" +
                "id_student=" + id_student +
                ", balance='" + balance + '\'' +
                ", certificate='" + certificate + '\'' +
                ", rating=" + rating +
                ", id_user=" + id_user +
                /*", id_group=" + id_group +*/
                '}';
    }
}
