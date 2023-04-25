package com.example.servercurs.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="language")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_language;
    @Column(name="name_language")
    private String name_language;

    @Override
    public String toString() {
        return "Language{" +
                "id_language=" + id_language +
                ", name_language='" + name_language + '\'' +
                '}';
    }
    @OneToMany(mappedBy = "id_language",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    List<Course> courses;
}
