package com.example.servercurs.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="timetable")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_timetable;
    @Column(name="date")
    private String date;
    @Column(name="time")
    private String time;
    //without foreight key
    //change type date and time on sql-types
}
