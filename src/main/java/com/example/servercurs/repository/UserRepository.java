package com.example.servercurs.repository;

import com.example.servercurs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT DISTINCT user FROM User user " +
            "JOIN FETCH user.role id_role")
    List<User> findWithRole();
    @Query("from User us where us.role.roleName=:admin")
    User findByRole(String admin);

    @Query("from User us where us.mail=:mail")
    User findByEmail(@Param("mail") String mail);

}
