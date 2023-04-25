package com.example.servercurs.service;

import com.example.servercurs.entities.Course;
import com.example.servercurs.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class  CourseService {
    private final CourseRepository courseRepository;
    private static final String BASE_PACKAGE = "com.example.servercurs";
    public CourseService() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(BASE_PACKAGE);
        courseRepository = context.getBean(CourseRepository.class);
    }
    // ticketObserver = new TicketObserver();
    @Autowired
    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public List<Course> findAllCourse(){
        return courseRepository.findAll();
    }
    public Course findById(int id){
        return courseRepository.findById(id).orElse(null);
    }
    public Course save(Course course){
        return courseRepository.save(course);
    }
    public void delete(int id){
        courseRepository.deleteById(id);
    }
}
