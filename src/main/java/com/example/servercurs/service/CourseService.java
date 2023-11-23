package com.example.servercurs.service;

import com.example.servercurs.entities.Course;
import com.example.servercurs.entities.Language;
import com.example.servercurs.entities.Skills;
import com.example.servercurs.entities.Trainee;
import com.example.servercurs.repository.CourseRepository;
import com.example.servercurs.repository.LanguageRepository;
import com.example.servercurs.repository.SkillsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final LanguageRepository languageRepository;
    private final SkillsRepository skillsRepository;
    private final TraineeService traineeService;

    public List<Course> findAllCourse() {
        return courseRepository.findAll();
    }

    public Course findById(int id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public void delete(int id) {
        courseRepository.deleteById(id);
    }

    public List<Course> findWithAll() {
        return courseRepository.findWithAll();
    }

    public List<Course> findCourseById_skills(Skills id) {
        return courseRepository.findCourseById_skills(id);
    }

    public Course findCourse(Skills id, Language idLang, String name) {
        return courseRepository.findCourse(id, idLang, name);
    }

    public List<Course> findCoursesByTeacherId(int id){
        return courseRepository.findCoursesByTeacherId(id);
    }

    public List<Object[]> findGroupedCourses() {
        return courseRepository.findGroupedCourses();
    }

    public List<Object[]> findGroupedCoursesLang() {
        return courseRepository.findGroupedCoursesLang();
    }

    public void update(int idCourse, String courseName, String level,
                       float price, int duration, String skills, String lang) {
        Language language = languageRepository.findLanguageByName_language(lang);
        Skills skills1 = skillsRepository.findSkillsByName_skills(skills);
        Course course = courseRepository.findById(idCourse).get();
        course.setCourse_name(courseName);
        course.setLevel(level);
        course.setDuration(duration);
        course.setPrice(price);
        course.setId_language(language);
        course.setId_skills(skills1);
        courseRepository.save(course);
    }

    public void addNewCourse(String courseName, String level,
                             float price, int duration, String skills, String lang) {
        Language language = languageRepository.findLanguageByName_language(lang);
        Skills skills1 = skillsRepository.findSkillsByName_skills(skills);
        Course course = new Course();
        course.setCourse_name(courseName);
        course.setLevel(level);
        course.setDuration(duration);
        course.setPrice(price);
        course.setId_language(language);
        course.setId_skills(skills1);
        courseRepository.save(course);
    }

    public List<String> encodedImageForTrainee(){
        List<Trainee> listCourse = traineeService.findWithAll();
        List<String> encodedImage = new ArrayList<>();
        List<Language> list = languageRepository.findAll();
        for (Trainee lg:listCourse) {
            for(Language lang: list) {
                String image = null;
                if (lg.getId_language().getId_language()==lang.getId_language()) {
                    image = Base64.getEncoder().encodeToString(lang.getLogo());
                    encodedImage.add(image);
                }
                else {
                    continue;
                }
            }
        }
        return encodedImage;
    }

}
