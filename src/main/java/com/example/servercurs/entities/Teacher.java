package com.example.servercurs.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Table(name="teacher")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_teacher;

    @Column(name="work_experience")
    private int work;

    @Column(name="speciality")
    private String speciality;
    @Column(name="rating")
    private Double rating;

    @Column(name="count_rating")
    @ColumnDefault("0")
    private String count_rating;

    @Column(name="ckeck")
    private String check;


    //without foreight keys
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_user")
    private User id_user;
    @OneToMany(mappedBy = "teacher",
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH)
    List<Group> groups;
   // one to many


    @Override
    public String toString() {
        return "Teacher{" +
                "id_teacher=" + id_teacher +
                ", work=" + work +
                ", speciality='" + speciality + '\'' +
                ", rating=" + rating +
                ", id_user=" + id_user +
                '}';
    }
}
