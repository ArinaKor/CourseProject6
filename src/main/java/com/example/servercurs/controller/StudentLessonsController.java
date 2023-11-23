package com.example.servercurs.controller;

import com.example.servercurs.entities.Course;
import com.example.servercurs.entities.CourseLesson;
import com.example.servercurs.entities.Student;
import com.example.servercurs.service.CourseLessonService;
import com.example.servercurs.service.CourseService;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student/group/")
@RequiredArgsConstructor
public class StudentLessonsController {

    private final StudentService studentService;
    private final GroupService groupService;
    private final CourseService courseService;
    private final CourseLessonService courseLessonService;

    @GetMapping("{id_student}")
    public String getPageStudentLessons(@PathVariable("id_student") int id_student, Model model){
        Student student = studentService.findById(id_student);
        Course course = courseService.findCourseByGroupId(student.getId_group().getId_group());
        List<CourseLesson> lessons = courseLessonService.findByCourse(course.getId_course());
        model.addAttribute("lessons", lessons);

        return "StudentLessonsCourse";
    }


}
