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

    @Column(name = "days_of_week")
    private String dayOfWeek;
    @Column(name = "time")
    private String time;

    @Override
    public String toString() {
        return "TimeTable{" +
                "id_timetable=" + id_timetable +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", time='" + time+
                '}';
    }
}
