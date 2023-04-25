package com.example.servercurs.service;

import com.example.servercurs.entities.Teacher;
import com.example.servercurs.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService{
    private final TeacherRepository teacherRepository;
    private static final String BASE_PACKAGE = "com.example.servercurs";
    public TeacherService() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(BASE_PACKAGE);
        teacherRepository = context.getBean(TeacherRepository.class);
    }
    // ticketObserver = new TicketObserver();
    @Autowired
    public TeacherService(TeacherRepository teacherRepository){
        this.teacherRepository = teacherRepository;
    }
    public List<Teacher> findAllTeachers(){
        return teacherRepository.findAll();
    }
    public Teacher findById(int id){
        return teacherRepository.findById(id).orElse(null);
    }
    public Teacher save(Teacher teacher){
        return teacherRepository.save(teacher);
    }
    public void delete(int id){
        teacherRepository.deleteById(id);
    }
}
