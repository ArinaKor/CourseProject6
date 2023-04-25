package com.example.servercurs.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_role;

    @Column(name = "role_name")
    private String roleName;

    @Override
    public String toString() {
        return "Role{" +
                "id_role=" + id_role +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    /* @OneToMany(mappedBy = "role_id_role",
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
        private List<User> users = new ArrayList<>();*/
   @OneToMany(mappedBy = "role",
           fetch = FetchType.LAZY,
           cascade = CascadeType.ALL)
   List<User> users;
}
