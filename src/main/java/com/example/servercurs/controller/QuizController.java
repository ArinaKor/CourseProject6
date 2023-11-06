package com.example.servercurs.controller;

import com.example.servercurs.entities.Course;
import com.example.servercurs.entities.Group;
import com.example.servercurs.entities.Language;
import com.example.servercurs.entities.Skills;
import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.Teacher;
import com.example.servercurs.entities.User;
import com.example.servercurs.service.CourseService;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.LanguageService;
import com.example.servercurs.service.SkillsService;
import com.example.servercurs.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final GroupService groupService;
    private final SkillsService skillsService;
    private final LanguageService languageService;

    @GetMapping("/{id}")
    public String doQuiz(@PathVariable("id") int id_stud, Model model) {
        Student student = studentService.findById(id_stud);
        model.addAttribute("student", student);
        return "QuizCourse";
    }

    @PostMapping("/res/{id}")
    public String checkQuiz(@PathVariable("id") int id_stud, @RequestParam int totalScore, Model model) {
        Student student = studentService.findById(id_stud);
        model.addAttribute("student", student);
        List<Course> listAll = courseService.findAllCourse();
        List<Course> list = new ArrayList<>();
        List<Group> listCourse = groupService.findAllGroups();

        List<Skills> skills = skillsService.findAllSkillss();
        System.out.println(totalScore);
        Skills skk = new Skills();
        int k = 0;
        if (totalScore >= 1 && totalScore <= 7) {
            for (Skills sk : skills) {
                if (sk.getName_skills().equals("Design")) {
                    k = sk.getId_skills();
                }
            }
            String work = "You are a future DESIGNER";
            model.addAttribute("work", work);
            list = courseService.findCourseById_skills(skillsService.findById(k));

        } else if (totalScore >= 8 && totalScore <= 10) {
            for (Skills sk : skills) {
                if (sk.getName_skills().equals("Analitics")) {
                    k = sk.getId_skills();
                }
            }
            String work = "You are a future Analitic";
            model.addAttribute("work", work);
            list = courseService.findCourseById_skills(skillsService.findById(k));
        } else if (totalScore >= 11 && totalScore <= 14) {
            for (Skills sk : skills) {
                if (sk.getName_skills().equals("Testing")) {
                    k = sk.getId_skills();
                }
            }
            String work = "You are a future Testing";
            model.addAttribute("work", work);
            list = courseService.findCourseById_skills(skillsService.findById(k));
        } else if (totalScore >= 15 && totalScore <= 21) {
            for (Skills sk : skills) {
                if (sk.getName_skills().equals("Programming")) {
                    k = sk.getId_skills();
                }
            }
            String work = "You are a future Programmer";
            model.addAttribute("work", work);
            list = courseService.findCourseById_skills(skillsService.findById(k));

        }
        Teacher teacher = new Teacher();
        User user = new User();
        for (Group gr : listCourse) {
            if (gr.getTeacher() == null) {
                user.setSurname("not found");
                user.setName("yet");
                teacher.setId_user(user);
                gr.setTeacher(teacher);
            }
        }
        listCourse = groupService.findByListCourse(list);
        model.addAttribute("list", listCourse);
        List<Skills> skillsList = skillsService.findAllSkillss();
        model.addAttribute("skills", skillsList);
        List<Language> langList = languageService.findAllLanguages();
        model.addAttribute("lang", langList);

        int f = 0;
        if (student.getId_group() == null) {
            f = 0;
        } else if (!(student.getId_group() == null)) {
            f++;
        }
        System.out.println(k);
        model.addAttribute("k", f);
        List<String> encodedImage = new ArrayList<>();
        List<Language> list1 = languageService.findAllLanguages();
        for (Group lg : listCourse) {
            String image = Base64.getEncoder().encodeToString(lg.getCourse().getId_language().getLogo());
            encodedImage.add(image);
        }
        model.addAttribute("encodedImage", encodedImage);
        return "QuizCourseRes";
    }
}
