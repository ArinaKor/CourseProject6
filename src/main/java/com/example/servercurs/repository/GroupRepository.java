package com.example.servercurs.repository;

import com.example.servercurs.entities.Group;
import com.example.servercurs.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    @Query("from Group gr order by gr.id_group")
    List<Group> findAllOrder();
   @Query("from Group gr where gr.teacher.id_teacher=:id order by gr.id_group")
    List<Group> findGroupsByTeacher(int id);
    @Query("SELECT DISTINCT st FROM Group st " +
            "JOIN FETCH st.timetable id_timetable")
    List<Group> findWithAll();
   /* @Query("SELECT t.timetable.id_timetable, COUNT(t) as count FROM Group t GROUP BY t.timetable.id_timetable HAVING COUNT(t) > 1")
    List<String> findAllByTimetable();*/
}
