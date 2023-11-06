package com.example.servercurs.controller;

import com.example.servercurs.entities.Course;
import com.example.servercurs.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GraphController {

    private final CourseService courseService;

    @GetMapping("/chart")
    public String chart(Model model) {
        List<Object[]> courses = courseService.findGroupedCourses();
        Map<String, Long> groupedCourses = new HashMap<>();
        for (Object[] course : courses) {
            String direction = (String) course[0];
            Long count = (Long) course[1];
            groupedCourses.put(direction, count);
        }
        System.out.println(groupedCourses);
        model.addAttribute("map", groupedCourses);

        List<Course> courseList = courseService.findAllCourse();
        List<Object[]> langs = courseService.findGroupedCoursesLang();
        model.addAttribute("data", langs);

        return "chart";
    }

    @GetMapping("/chart/students")
    public String ckeckStudentsStatistic(Model model){

        return "";
    }
}
