package com.example.servercurs.service;

import com.example.servercurs.entities.Teacher;
import com.example.servercurs.entities.User;
import com.example.servercurs.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public List<Teacher> findAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher findById(int id) {
        return teacherRepository.findById(id).orElse(null);
    }

    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public void delete(int id) {
        teacherRepository.deleteById(id);
    }

    public Teacher findTeacherById_user(User id_user){
        return teacherRepository.findTeacherById_user(id_user);
    }

    public List<Teacher> findTeacherByCheck(String check){
        return teacherRepository.findTeacherByCheck(check);
    }

}
