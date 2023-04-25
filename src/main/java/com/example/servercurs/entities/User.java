package com.example.servercurs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_user;

    @Column(name="surname")
    private String surname;

    @Column(name="name")
    private String name;

    @Column(name="password")
    private String password;
    @Column(name="mail")
    private String mail;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="role", nullable = false)
    @JsonIgnore
    private Role role;
    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="role_id_role", nullable = false)
    @JsonIgnore
    private Role role_id_role;
    @OneToOne(mappedBy = "id_user")
    private Student student;*/

    @Override
    public String toString() {
        return "User{" +
                "id_user=" + id_user +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", role=" + role +
                '}';
    }

}
