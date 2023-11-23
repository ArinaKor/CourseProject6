package com.example.servercurs.service;

import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.User;
import com.example.servercurs.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    @Transactional
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
    public Optional<Student> findByUser(int user){
        return studentRepository.findById_user(user);
    }


}
