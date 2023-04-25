package com.example.servercurs.repository;

import com.example.servercurs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT DISTINCT user FROM User user " +
            "JOIN FETCH user.role id_role")
    List<User> findWithRole();
/*
    @Query("SELECT User.id_user, User.surname, User.name, User.login, User.password, User.mail , Role.roleName FROM User INNER JOIN Role ")
    List<User> joinUserAndRole();
    =========================
    select user.id_user, user.surname,user.name, user.login, user.password, user.mail, role.role_name from user
inner join role on user.role = role.id_role
    */
    /*@Query("select us.id_user, us.surname, us.name, us.login, us.password, us.mail, role.roleName from User us inner join Role role on us.role = role.id_role")
    List<User> findWithRole();*/

}
