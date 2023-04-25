package com.example.servercurs.repository;

import com.example.servercurs.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("From Role r where r.roleName=:name")
    Role findRoleByRoleName(String name);

}
