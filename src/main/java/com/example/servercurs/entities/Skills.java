package com.example.servercurs.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="skills")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_skills;
    @Column(name="name_skills")
    private String name_skills;

    @Override
    public String toString() {
        return "Skills{" +
                "idSkils=" + id_skills +
                ", name_skils='" + name_skills + '\'' +
                '}';
    }
    @OneToMany(mappedBy = "id_skills",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    List<Course> courses;
}
