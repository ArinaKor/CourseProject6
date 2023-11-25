package com.example.servercurs.service;

import com.example.servercurs.entities.CourseLesson;
import com.example.servercurs.repository.CourseLessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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
                                String linkList) {
        List<CourseLesson> courseLessons = courseLessonRepository.findByCourse(idCourse);
        int count = courseLessons.size() + 1;
        CourseLesson courseLesson = new CourseLesson();
        courseLesson.setLessonName(lessonName);
        courseLesson.setLessonText(text);
        courseLesson.setId_course(courseService.findById(idCourse));
        courseLesson.setLinks(getLinksList(linkList));
        courseLesson.setNumberLesson(count);
        courseLessonRepository.save(courseLesson);
    }

    public List<CourseLesson> findByCourse(int id){
        return courseLessonRepository.findByCourse(id);
    }

    public List<String> getLinksList(String links){
        String lnk = links.replace("[", "");
        String resLnk = lnk.replace("]", "");
        System.out.println(resLnk);
        String[] result = resLnk.split(",");
        List<String> linksListRes = new ArrayList<>();
        for (String st:result) {
            System.out.println(st);
            String st1 = st.replace("\"", "");
            linksListRes.add(st1);
        }

        return linksListRes;
    }

    public CourseLesson findByNumber(int num, int course){
        return courseLessonRepository.findByNumber(num, course);
    }
}
