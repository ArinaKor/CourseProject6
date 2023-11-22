package com.example.servercurs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    /*@ColumnDefault("0")*/
    private double rating;

    @Column(name="courses")
    private String courses;

    @Column(name="count_rating")
    private String count_rating;

    //without foreight keys
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="id_user")
    private User id_user;


    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name="id_group")
    @JsonIgnore
    private Group id_group;

    @OneToMany(mappedBy = "idHistory",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<LessonsHistory> lessonsHistories;

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
