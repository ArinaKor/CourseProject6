package com.example.servercurs.controller;

import com.example.servercurs.entities.Course;
import com.example.servercurs.entities.Language;
import com.example.servercurs.entities.Skills;
import com.example.servercurs.service.CourseService;
import com.example.servercurs.service.LanguageService;
import com.example.servercurs.service.SkillsService;
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
@RequestMapping("/admin/courses")
@RequiredArgsConstructor
public class CoursesController {

    private final CourseService courseService;
    private final SkillsService skillsService;
    private final LanguageService languageService;

    @GetMapping
    public String workWithCourses(Model model, RedirectAttributes attributes){
        List<Course> listCourse = courseService.findWithAll();
        model.addAttribute("list", listCourse);
        List<String> encodedImage = new ArrayList<>();
        List<Language> list = languageService.findAllLanguages();
        for (Course lg:listCourse) {
            for(Language lang: list) {
                String image = null;
                if (lg.getId_language().getId_language()==lang.getId_language()) {
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
        return "AdminCourses";

    }

    @GetMapping("/add")
    public String AddCourse(Model model){
        List<Skills> listSkills = skillsService.findAllSkillss();
        List<Language> listLang = languageService.findAllLanguages();
        model.addAttribute("listSkills", listSkills);
        model.addAttribute("listLang", listLang);

        return "AddCourses";
    }

    @PostMapping("/add")
    public String addNewCourse(@RequestParam String course_name, @RequestParam String level, @RequestParam float price, @RequestParam int duration, @RequestParam String skills, @RequestParam String lang){
        courseService.addNewCourse(course_name, level, price, duration, skills, lang);
        return "redirect:/admin/courses";
    }

    @PostMapping("/{id_course}/delete")
    public String delete(@PathVariable(value = "id_course") int id_course){
        courseService.delete(id_course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/{id_course}/edit")
    public String edit(@PathVariable(value = "id_course") int id_course, Model model){
        Course course = courseService.findById(id_course);
        model.addAttribute("el", course);
        List<Skills> listSkills = skillsService.findAllSkillss();
        List<Language> listLang = languageService.findAllLanguages();
        model.addAttribute("listSkills", listSkills);
        model.addAttribute("listLang", listLang);

        return "EditCourses";
    }

    @PostMapping("/{id_course}/edit")
    public String update(@PathVariable(value = "id_course") int id_course, @RequestParam String course_name, @RequestParam String level, @RequestParam float price, @RequestParam int duration, @RequestParam String skills, @RequestParam String lang, Model model){
        courseService.update(id_course, course_name, level, price, duration, skills, lang);
        return "redirect:/admin/courses";
    }

}
