package com.example.servercurs.repository;

import com.example.servercurs.entities.Course;
import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.Teacher;
import com.example.servercurs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    @Query("from Student tc where tc.id_user=:id_user")
    Student findStudentById_user(User id_user);
    @Query("SELECT DISTINCT st FROM Student st " +
            "JOIN FETCH st.id_group id_group")
    List<Student> findWithAll();
}
