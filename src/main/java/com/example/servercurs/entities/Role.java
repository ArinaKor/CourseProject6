package com.example.servercurs.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

   @OneToMany(mappedBy = "role",
           fetch = FetchType.LAZY,
           cascade = CascadeType.ALL)
   private List<User> users;
}
