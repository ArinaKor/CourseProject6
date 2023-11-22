package com.example.servercurs.entities;

import com.example.servercurs.entities.enums.StatusLesson;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "lessons_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LessonsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_history")
    private Integer idHistory;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_lesson")
    private StatusLesson statusLesson;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_course", nullable = false)
    @JsonIgnore
    private Course id_course;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idLesson", nullable = false)
    @JsonIgnore
    private CourseLesson idLesson;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_student", nullable = false)
    @JsonIgnore
    private Student id_student;

}
