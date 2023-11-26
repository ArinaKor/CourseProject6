package com.example.servercurs.controller;

import com.example.servercurs.entities.Course;
import com.example.servercurs.entities.CourseLesson;
import com.example.servercurs.entities.LessonsHistory;
import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.enums.StatusLesson;
import com.example.servercurs.service.CourseLessonService;
import com.example.servercurs.service.CourseService;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.LessonsHistoryService;
import com.example.servercurs.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/student/group/")
@RequiredArgsConstructor
public class StudentLessonsController {

    private final StudentService studentService;
    private final GroupService groupService;
    private final CourseService courseService;
    private final CourseLessonService courseLessonService;
    private final LessonsHistoryService lessonsHistoryService;

    @GetMapping("{id_student}")
    public String getPageStudentLessons(@PathVariable("id_student") int id_student, Model model, RedirectAttributes attributes){
        Student student = studentService.findById(id_student);
        if (student.getId_group() == null) {
            String msg = "Никакой курс не проходится";
            attributes.addFlashAttribute("msg", msg);
            model.addAttribute("student", student);
            attributes.addFlashAttribute("student", student);
            return "redirect:/student/" + student.getId_student();
        }
        Course course = courseService.findCourseByGroupId(student.getId_group().getId_group());
        List<CourseLesson> lessons = courseLessonService.findByCourse(course.getId_course());
        model.addAttribute("lessons", lessons);/*
        model.addAttribute("service", courseLessonService);*/
        model.addAttribute("student", student);
        List<LessonsHistory> lessonsHistories = lessonsHistoryService.findByStudentAndCourse(id_student);
        //System.out.println(lessonsHistories.toString());

        model.addAttribute("history", lessonsHistories);
        return "StudentLessonsCourse";
    }

    //@RequestBody String lesson
    @PostMapping("/{studentId}/{lessonId}/check")
    public ResponseEntity<String> checkLesson(@PathVariable("studentId") int studentId, @PathVariable("lessonId") int lessonId) {
        Student student = studentService.findById(studentId);
        CourseLesson courseLesson = courseLessonService.findByNumber(lessonId, student.getId_group().getCourse().getId_course());
        LessonsHistory lessonsHistory = new LessonsHistory();
        lessonsHistory.setIdLesson(courseLesson);
        lessonsHistory.setId_student(student);
        lessonsHistory.setId_course(student.getId_group().getCourse());
        lessonsHistory.setStatusLesson(StatusLesson.DONE);
        lessonsHistoryService.save(lessonsHistory);
        /*LessonsHistory lessonsHistory = new LessonsHistory();
        lessonsHistory.setIdLesson(courseLesson);
        lessonsHistory.setId_student(student);
        lessonsHistory.setId_course(student.getId_group().getCourse());
        lessonsHistory.setStatusLesson(StatusLesson.DONE);
        if (lessonsHistoryService.findExistingRecord(StatusLesson.DONE,student.getId_group().getCourse(), courseLesson, student).isEmpty());
        {
            lessonsHistoryService.save(lessonsHistory);
        }*/
        //можно сделать проверку на наличие этой записи в бд
        return ResponseEntity.ok("Урок успешно проверен");
    }
}
