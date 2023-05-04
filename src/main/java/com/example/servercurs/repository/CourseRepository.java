package com.example.servercurs.repository;

import com.example.servercurs.entities.Course;
import com.example.servercurs.entities.Language;
import com.example.servercurs.entities.Skills;
import com.example.servercurs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT DISTINCT course FROM Course course " +
            "JOIN FETCH course.id_skills id_skills JOIN FETCH course.id_language id_language")
    List<Course> findWithAll();
    @Query("From Course c where c.id_skills=:id")
    List<Course> findCourseById_course(Skills id);
    @Query("From Course c where c.id_skills=:id and c.id_language=:idLang and c.course_name=:name")
    Course findCourse(Skills id, Language idLang, String name);
    @Query("SELECT c FROM Course c WHERE c.id_skills.id_skills IN :selected")
    List<Course> findBySkills(List<Integer> selected);
    @Query("SELECT c FROM Course c WHERE c.id_language.id_language IN :finding")
    List<Course> findByLang(List<Integer> finding);

    @Query("SELECT c.id_skills.name_skills, COUNT(c) FROM Course c GROUP BY c.id_skills")
    List<Object[]> findGroupedCourses();
    @Query("SELECT c.id_language.name_language, COUNT(c) FROM Course c GROUP BY c.id_language")
    List<Object[]> findGroupedCoursesLang();
}
