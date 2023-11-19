package com.example.servercurs.entities;

import com.example.servercurs.entities.enums.TraineeType;
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


@Entity
@Table(name="trainee")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Trainee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="trainee_id")
    private Integer traineeId;

    private int duration;

    //@Enumerated(EnumType.STRING)
    private String location;

    @Enumerated(EnumType.STRING)
    private TraineeType type;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="id_skills", nullable = false)
    @JsonIgnore
    private Skills id_skills;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="id_language", nullable = false)
    @JsonIgnore
    private Language id_language;

}
