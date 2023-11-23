package com.example.servercurs.repository;


import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    @Query("from Student tc where tc.id_user=:id_user")
    Student findStudentById_user(User id_user);

    @Query("SELECT DISTINCT st FROM Student st " +
            "JOIN FETCH st.id_group id_group")
    List<Student> findWithAll();

    @Query("from Student gr where gr.id_group.id_group=:id order by gr.id_user.surname")
    List<Student> findStudentByGroup(int id);

    @Query("from Student st where st.id_user.id_user =: user")
    Optional<Student> findById_user(@Param("user") int user);

}
