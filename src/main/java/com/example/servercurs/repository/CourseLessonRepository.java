package com.example.servercurs.repository;

import com.example.servercurs.entities.CourseLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseLessonRepository extends JpaRepository<CourseLesson, Integer> {
}
