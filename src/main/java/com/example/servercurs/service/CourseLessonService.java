package com.example.servercurs.service;

import com.example.servercurs.entities.CourseLesson;
import com.example.servercurs.repository.CourseLessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseLessonService {

    private final CourseLessonRepository courseLessonRepository;

    public List<CourseLesson> findAllCourse() {
        return courseLessonRepository.findAll();
    }

    public CourseLesson findById(int id) {
        return courseLessonRepository.findById(id).orElse(null);
    }

    public CourseLesson save(CourseLesson lesson) {
        return courseLessonRepository.save(lesson);
    }

    public void delete(int id) {
        courseLessonRepository.deleteById(id);
    }
}
