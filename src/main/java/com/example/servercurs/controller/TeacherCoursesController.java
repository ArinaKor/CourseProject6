package com.example.servercurs.controller;

import com.example.servercurs.entities.Course;
import com.example.servercurs.entities.CourseLesson;
import com.example.servercurs.entities.Group;
import com.example.servercurs.entities.Language;
import com.example.servercurs.entities.Student;
import com.example.servercurs.repository.CourseLessonRepository;
import com.example.servercurs.repository.CourseRepository;
import com.example.servercurs.service.CourseLessonService;
import com.example.servercurs.service.CourseService;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.LanguageService;
import com.example.servercurs.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/teacher/courses")
@RequiredArgsConstructor
public class TeacherCoursesController {

    private final CourseLessonService lessonService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final LanguageService languageService;
    private final GroupService groupService;


    @GetMapping("/{id_teacher}/new-lesson/{id}")
    public String submit(@PathVariable("id_teacher") int id_teacher,@PathVariable("id") int idCourse, Model model){
        model.addAttribute("idCourse", idCourse);
        model.addAttribute("id_teacher", teacherService.findById(id_teacher).getId_teacher());
        return "CreateLessonTeacher";
    }

    @PostMapping("/new-lesson/{id}")
    public String submitNewCourse(@RequestParam("id_teacher") int id_teacher,
                                  @PathVariable("id") int idCourse,
                                  @RequestParam("lessonName") String lessonName,
                                  @RequestParam("text") String text,
                                  @RequestParam("linkList") String linkList, RedirectAttributes redirectAttributes){
        lessonService.submitNewCourse(idCourse, lessonName, text, linkList);
        redirectAttributes.addFlashAttribute("id_teacher", teacherService.findById(id_teacher).getId_teacher());
        return "redirect:/teacher/courses/"+id_teacher+"/add";
    }

    @GetMapping("/{id_teacher}/add")
    public String chooseCourse(@PathVariable("id_teacher") int id_teacher,
                               Model model, RedirectAttributes attributes){
        List<String> encodedImage = new ArrayList<>();
        List<Group> list = groupService.findAllGroupsByTeacher(id_teacher);
        List<Language> list1 = languageService.findAllLanguages();
        for (Group lg:list) {
            for(Language lang: list1) {
                String image = null;
                if (lg.getCourse().getId_language().getId_language()==lang.getId_language()) {
                    image = Base64.getEncoder().encodeToString(lang.getLogo());
                    encodedImage.add(image);
                }
                else {
                    continue;
                }
            }
        }
        model.addAttribute("encodedImage", encodedImage);
        attributes.addFlashAttribute("encodedImage", encodedImage);
        model.addAttribute("teacher", teacherService.findById(id_teacher));
        attributes.addFlashAttribute("teacher", teacherService.findById(id_teacher));
        List<Course> courses = courseService.findCoursesByTeacherId(id_teacher);
        //System.out.println(courses);
        model.addAttribute("courses", courses);
        attributes.addFlashAttribute("courses", courses);
        return "ChooseCourseForCreate";
    }

}
