package com.example.servercurs.service;

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
import com.example.servercurs.repository.LessonsHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonsHistoryService {
    private final LessonsHistoryRepository lessonsHistoryRepository;

    public List<LessonsHistory> findAllCourse() {
        return lessonsHistoryRepository.findAll();
    }

    public LessonsHistory findById(int id) {
        return lessonsHistoryRepository.findById(id).orElse(null);
    }

    public LessonsHistory save(LessonsHistory lesson) {
        return lessonsHistoryRepository.save(lesson);
    }

    public void delete(int id) {
        lessonsHistoryRepository.deleteById(id);
    }

    public List<LessonsHistory> findByStudentAndCourse(int stud){
        return lessonsHistoryRepository.findByStudentAndCourse(stud);
    }

    public Optional<LessonsHistory> findExistingRecord(StatusLesson statusLesson,

                                             CourseLesson idLesson,
                                              Student id_student){
        return lessonsHistoryRepository.findExistingRecord(statusLesson,  idLesson, id_student);
    }
}
