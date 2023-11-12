package com.example.servercurs.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="timetable")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class TimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_timetable;

    /*@Enumerated(EnumType.STRING)*/
    @Column(name = "days_of_week")
    private String dayOfWeek;
    @Column(name = "time")
    private String time;

    /*@Column(name="time")
    private String time;*/
    //without foreight key
    //change type date and time on sql-types
    //onetomany
    /*@OneToMany(mappedBy = "timetable",
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH)
    List<Group> groups;*/

    @Override
    public String toString() {
        return "TimeTable{" +
                "id_timetable=" + id_timetable +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", time='" + time+
                '}';
    }
}
