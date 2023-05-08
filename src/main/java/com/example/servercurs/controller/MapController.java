package com.example.servercurs.controller;

import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.Teacher;
import com.example.servercurs.entities.User;
import com.example.servercurs.repository.StudentRepository;
import com.example.servercurs.repository.TeacherRepository;
import com.example.servercurs.service.StudentService;
import com.example.servercurs.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class MapController {

    private static final String API_KEY = "9d4a8d8c-7bb1-4267-891c-7f1c8bbd0509";
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping("/maps/{id}")
    public String showMap(@PathVariable("id") int id_user, Model model, RedirectAttributes attributes) {
        User user = userService.findById(id_user);
        model.addAttribute("user", user);
        Student student = studentRepository.findStudentById_user(user);
        model.addAttribute("student", student);
        Teacher teacher = teacherRepository.findTeacherById_user(user);
        model.addAttribute("teacher" , teacher);
        /*Student student = studentService.findById(id);*/
        String address = "Минск, улица Петруся Бровки 39";
        model.addAttribute("address", address);
        model.addAttribute("apiKey", API_KEY);/*
        model.addAttribute("student", student);
        attributes.addFlashAttribute("student", student);*/
        return "YandexMaps";
    }

}
