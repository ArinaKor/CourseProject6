package com.example.servercurs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name="course")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_course;

    @Column(name="course_name")
    private String course_name;

    @Column(name="level")
    private String level;

    @Column(name="price")
    private double price;

    @Column(name="duration")
    private int duration;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="id_skills", nullable = false)
    @JsonIgnore
    private Skills id_skills;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="id_language", nullable = false)
    @JsonIgnore
    private Language id_language;

    @OneToMany(mappedBy = "course",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Group> groups;

    @OneToMany(mappedBy = "idHistory",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<LessonsHistory> lessonsHistories;

    @OneToMany(mappedBy = "idLesson",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<CourseLesson> lessons;

    @Override
    public String toString() {
        return "Course{" +
                "id_course=" + id_course +
                ", course_name='" + course_name + '\'' +
                ", level='" + level + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", id_skills=" + id_skills +
                ", id_language=" + id_language +
                '}';
    }
}
