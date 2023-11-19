package com.example.servercurs.entities;

import com.example.servercurs.entities.enums.EnglishLevel;
import com.example.servercurs.entities.enums.Location;
import com.example.servercurs.entities.enums.StatusReply;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trainee_reply")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TraineeReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String surname;
    private String name;
    @Enumerated(EnumType.STRING)
    private Location location;
    private String town;
    private String dateBirth;
    private String mail;
    @Enumerated(EnumType.STRING)
    private EnglishLevel englishLevel;
    private String education;

    @Enumerated(EnumType.STRING)
    private StatusReply statusReply;
    private int testResult;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_skills")
    private Skills idSkills;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_language")
    private Language idLanguage;

}
