package com.example.servercurs.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="teacher")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_teacher;

    @Column(name="work_experience")
    private int work;

    @Column(name="speciality")
    private String speciality;
    //without foreight keys
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_user")
    private User id_user;
}
