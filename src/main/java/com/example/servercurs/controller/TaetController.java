package com.example.servercurs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teacher/courses/")
public class TaetController {
    @GetMapping("new-lesson")
    public String submit(Model model){
        return "CreateLessonTeacher";
    }
}
