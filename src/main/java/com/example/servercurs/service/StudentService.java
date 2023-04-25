package com.example.servercurs.service;

import com.example.servercurs.entities.Student;
import com.example.servercurs.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private static final String BASE_PACKAGE = "com.example.servercurs";
    public StudentService() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(BASE_PACKAGE);
        studentRepository = context.getBean(StudentRepository.class);
    }
    // ticketObserver = new TicketObserver();
    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
    public List<Student> findAllStudents(){
        return studentRepository.findAll();
    }
    public Student findById(int id){
        return studentRepository.findById(id).orElse(null);
    }
    public Student save(Student student){
        return studentRepository.save(student);
    }
    public void delete(int id){
        studentRepository.deleteById(id);
    }
}
