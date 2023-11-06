package com.example.servercurs.repository;

import com.example.servercurs.entities.Course;
import com.example.servercurs.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
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

    @Query("FROM Group g where g.date_start=:date")
    List<Group> findByDateStart(Date date);

    @Query("FROM Group g where g.group_time=:time")
    List<Group> findByGroup_time(String time);

    @Query("FROM Group g where g.course=:course")
    List<Group> findByCourse(Course course);

    @Query("SELECT c FROM Group c WHERE c.course IN :course")
    List<Group> findByListCourse(List<Course> course);

}
