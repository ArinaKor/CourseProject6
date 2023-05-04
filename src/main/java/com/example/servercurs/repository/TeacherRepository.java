package com.example.servercurs.repository;

import com.example.servercurs.entities.Teacher;
import com.example.servercurs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    @Query("from Teacher tc where tc.id_user=:id_user")
    Teacher findTeacherById_user(User id_user);
    @Query("from Teacher tc where tc.check=:check")
    List<Teacher> findTeacherByCheck(String check);
}
