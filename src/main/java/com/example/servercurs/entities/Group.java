package com.example.servercurs.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name="group1")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_group;

    @Column(name="count_student_all")
    private int count_student_all;

    @Column(name="group_time")
    private String group_time;

    @Column(name="recorded_count")
    private int recorded_count;

    @Column(name="date_start")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date date_start;
    //without foreight keys
  //  @OneToMany(mappedBy = "id_group",
    //        fetch = FetchType.LAZY,
      //      cascade = CascadeType.ALL)
  //  private List<Student> student;
/*
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_teacher")
    private Teacher id_teacher;*/
}
