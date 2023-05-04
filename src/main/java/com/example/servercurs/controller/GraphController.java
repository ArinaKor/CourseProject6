package com.example.servercurs.controller;

import com.example.servercurs.entities.Course;
import com.example.servercurs.entities.Skills;
import com.example.servercurs.repository.CourseRepository;
import com.example.servercurs.service.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GraphController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SkillsService skillsService;

    @GetMapping("/chart")
    public String chart(Model model) {
        // Map<String, Long> courseCountByDirection = new HashMap<>();
        List<Object[]> courses = courseRepository.findGroupedCourses();
        Map<String, Long> groupedCourses = new HashMap<>();
        for (Object[] course : courses) {
            String direction = (String) course[0];
            Long count = (Long) course[1];
            groupedCourses.put(direction, count);
        }
        System.out.println(groupedCourses);
        model.addAttribute("map", groupedCourses);


        List<Course> courseList = courseRepository.findAll();
        List<Object[]> langs = courseRepository.findGroupedCoursesLang();
        model.addAttribute("data", langs);

        return "chart";
    }

    @GetMapping("/chart/students")
    public String ckeckStudentsStatistic(Model model){

        return "";
    }
}
