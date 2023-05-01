package com.example.servercurs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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



    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="course", nullable = false)
    @JsonIgnore
    private Course course;

    @OneToMany(mappedBy = "id_group",
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH)
    List<Student> students;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="teacher")
    @JsonIgnore
    private Teacher teacher;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="timetable")
    private TimeTable timetable;

   /* @Override
    public String toString() {
        return "Group{" +
                "id_group=" + id_group +
                ", count_student_all=" + count_student_all +
                ", group_time='" + group_time + '\'' +
                ", recorded_count=" + recorded_count +
                ", date_start=" + date_start +
                ", course=" + course +
                ", students=" + students +
                ", teacher=" + teacher +
                ", timetable=" + timetable +
                '}';
    }*/
}
