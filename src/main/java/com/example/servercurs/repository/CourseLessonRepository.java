package com.example.servercurs.repository;

import com.example.servercurs.entities.CourseLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseLessonRepository extends JpaRepository<CourseLesson, Integer> {
    @Query("from CourseLesson c where c.id_course.id_course=:id")
    List<CourseLesson> findByCourse(@Param("id") int id);
}
