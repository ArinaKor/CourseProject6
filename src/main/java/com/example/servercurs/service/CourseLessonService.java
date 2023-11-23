package com.example.servercurs.service;

import com.example.servercurs.entities.CourseLesson;
import com.example.servercurs.repository.CourseLessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseLessonService {

    private final CourseLessonRepository courseLessonRepository;
    private final CourseService courseService;


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

    public void submitNewCourse(int idCourse,
                                String lessonName,
                                 String text,
                                 String linkList){
        CourseLesson courseLesson = new CourseLesson();
        courseLesson.setLessonName(lessonName);
        courseLesson.setLessonText(text);
        courseLesson.setId_course(courseService.findById(idCourse));
        courseLesson.setLinks(linkList);
        courseLessonRepository.save(courseLesson);
        
    }
}
