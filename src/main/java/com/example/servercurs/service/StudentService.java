package com.example.servercurs.service;

import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.User;
import com.example.servercurs.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Student findById(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public void delete(int id) {
        studentRepository.deleteById(id);
    }

    public Student findStudentById_user(User id_user){
        return studentRepository.findStudentById_user(id_user);
    }
    public List<Student> findWithAll(){
        return studentRepository.findWithAll();
    }
    public List<Student> findStudentByGroup(int id){
        return studentRepository.findStudentByGroup(id);
    }


}
