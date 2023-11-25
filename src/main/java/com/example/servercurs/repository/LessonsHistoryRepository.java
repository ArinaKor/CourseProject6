package com.example.servercurs.repository;

import com.example.servercurs.entities.Course;
import com.example.servercurs.entities.CourseLesson;
import com.example.servercurs.entities.Language;
import com.example.servercurs.entities.LessonsHistory;
import com.example.servercurs.entities.Skills;
import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.TraineeReply;
import com.example.servercurs.entities.enums.EnglishLevel;
import com.example.servercurs.entities.enums.Location;
import com.example.servercurs.entities.enums.StatusLesson;
import com.example.servercurs.entities.enums.StatusReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonsHistoryRepository extends JpaRepository<LessonsHistory, Integer> {
    @Query("from LessonsHistory history where history.id_student.id_student=:student")
    List<LessonsHistory> findByStudentAndCourse(@Param("student") int student);

    @Query("SELECT lh FROM LessonsHistory lh WHERE lh.statusLesson = :statusLesson AND lh.id_course = :id_course AND lh.idLesson = :idLesson AND lh.id_student = :id_student")
    Optional<LessonsHistory> findExistingRecord(@Param("statusLesson") StatusLesson statusLesson, @Param("id_course") Course id_course, @Param("idLesson") CourseLesson idLesson, @Param("id_student") Student id_student);
}
