package com.example.servercurs.controller;

import com.example.servercurs.entities.*;
import com.example.servercurs.repository.CourseRepository;
import com.example.servercurs.repository.GroupRepository;
import com.example.servercurs.repository.LanguageRepository;
import com.example.servercurs.repository.SkillsRepository;
import com.example.servercurs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CoursesController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SkillsService skillsService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private SkillsRepository skillsRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupService groupService;
    @GetMapping("/admin/courses")
    public String workWithCourses(Model model){
        List<Course> listCourse = courseRepository.findWithAll();
        model.addAttribute("list", listCourse);
        return "AdminCourses";

    }
    @GetMapping("/admin/courses/add")
    public String AddCourse(Model model){
        List<Skills> listSkills = skillsService.findAllSkillss();
        List<Language> listLang = languageService.findAllLanguages();
        model.addAttribute("listSkills", listSkills);
        model.addAttribute("listLang", listLang);

        return "AddCourses";
    }
    @PostMapping("/admin/courses/add")
    public String AddNewCourse(@RequestParam String course_name, @RequestParam String level, @RequestParam float price, @RequestParam int duration, @RequestParam String skills, @RequestParam String lang, Model model ){
        Language language = languageRepository.findLanguageByName_language(lang);
        Skills skills1 = skillsRepository.findSkillsByName_skills(skills);
        Course course = new Course();
       course.setCourse_name(course_name);
       course.setLevel(level);
       course.setDuration(duration);
       course.setPrice(price);
       course.setId_language(language);
       course.setId_skills(skills1);
       courseService.save(course);


        return "redirect:/admin/courses";
    }

    @PostMapping("/admin/courses/{id_course}/delete")
    public String delete(@PathVariable(value = "id_course") int id_course, Model model){
        courseService.delete(id_course);
        return "redirect:/admin/courses";
    }
    @GetMapping("/admin/courses/{id_course}/edit")
    public String edit(@PathVariable(value = "id_course") int id_course, Model model){
        Course course = courseService.findById(id_course);
        model.addAttribute("el", course);
        List<Skills> listSkills = skillsService.findAllSkillss();
        List<Language> listLang = languageService.findAllLanguages();
        model.addAttribute("listSkills", listSkills);
        model.addAttribute("listLang", listLang);

        return "EditCourses";
    }
    @PostMapping("/admin/courses/{id_course}/edit")
    public String update(@PathVariable(value = "id_course") int id_course, @RequestParam String course_name, @RequestParam String level, @RequestParam float price, @RequestParam int duration, @RequestParam String skills, @RequestParam String lang, Model model){
        Language language = languageRepository.findLanguageByName_language(lang);
        Skills skills1 = skillsRepository.findSkillsByName_skills(skills);
        Course course = courseService.findById(id_course);
        course.setCourse_name(course_name);
        course.setLevel(level);
        course.setDuration(duration);
        course.setPrice(price);
        course.setId_language(language);
        course.setId_skills(skills1);
        courseService.save(course);
        return "redirect:/admin/courses";
    }
    @GetMapping("/quiz/{id}")
    public String doQuiz(@PathVariable("id") int id_stud, Model model){
        Student student =  studentService.findById(id_stud);
        model.addAttribute("student", student);
        return "QuizCourse";
    }
    @PostMapping("/quiz/res/{id}")
    public String checkQuiz(@PathVariable("id") int id_stud,@RequestParam int totalScore, Model model){
        Student student =  studentService.findById(id_stud);
        model.addAttribute("student", student);
        List<Course> listAll = courseRepository.findAll();
        List<Course> list = new ArrayList<>();
        List<Group> listCourse = groupService.findAllGroups();

        List<Skills> skills = skillsService.findAllSkillss();
        System.out.println(totalScore);
        Skills skk = new Skills();
        int k = 0;
        if(totalScore>=1&&totalScore<=7){
            for (Skills sk: skills) {
                if(sk.getName_skills().equals("Design")){
                    k = sk.getId_skills();
                }
            }
            String work = "You are a future DESIGNER";
            model.addAttribute("work", work);
            list = courseRepository.findCourseById_course(skillsService.findById(k));
           //model.addAttribute("list", list);

        }


        else if(totalScore>=8&&totalScore<=10){
            for (Skills sk: skills) {
                if(sk.getName_skills().equals("Analitics")){
                    k = sk.getId_skills();
                }
            }
            String work = "You are a future Analitic";
            model.addAttribute("work", work);
            list = courseRepository.findCourseById_course(skillsService.findById(k));
           // model.addAttribute("list", list);

        }
        else if(totalScore>=11&&totalScore<=14){
            for (Skills sk: skills) {
                if(sk.getName_skills().equals("Testing")){
                    k = sk.getId_skills();
                }
            }
            String work = "You are a future Testing";
            model.addAttribute("work", work);
            list = courseRepository.findCourseById_course(skillsService.findById(k));
           //model.addAttribute("list", list);

        }
        else if(totalScore>=15&&totalScore<=21){
            for (Skills sk: skills) {
                if(sk.getName_skills().equals("Programming")){
                    k = sk.getId_skills();
                }
            }
            String work = "You are a future Programmer";
            model.addAttribute("work", work);
            list = courseRepository.findCourseById_course(skillsService.findById(k));
           //model.addAttribute("list", list);

        }
        Teacher teacher = new Teacher();
        User user = new User();
        for (Group gr:listCourse) {
            if(gr.getTeacher()==null){
                user.setSurname("not found");
                user.setName("yet");
                teacher.setId_user(user);
                gr.setTeacher(teacher);
            }


        }
        //model.addAttribute("tch", tch);
        listCourse = groupRepository.findByCourse(list);
        model.addAttribute("list", listCourse);
        List<Skills> skillsList = skillsService.findAllSkillss();
        model.addAttribute("skills", skillsList);
        List<Language> langList = languageService.findAllLanguages();
        model.addAttribute("lang", langList);

        int f=0;
        if(student.getId_group()==null){
            f=0;
        }
        else if(!(student.getId_group()==null)){
            f++;
        }
        System.out.println(k);
        model.addAttribute("k", f);

        return "FindGroupsStudent";
    }
}
