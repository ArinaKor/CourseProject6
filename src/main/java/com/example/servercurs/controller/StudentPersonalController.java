package com.example.servercurs.controller;

import com.example.servercurs.Certificate.FindLastGroup;
import com.example.servercurs.entities.*;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.StudentService;
import com.example.servercurs.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class StudentPersonalController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    FindLastGroup findLastGroup = new FindLastGroup();
    @GetMapping("/student/{id}")
    private String checkPersonalPage(@PathVariable(name="id") int id, RedirectAttributes attributes, Model model){
        Student student = studentService.findById(id);

        Group group = new Group();
        Course course1 = new Course();
        Skills skills = new Skills();
        Language language = new Language();

        skills.setName_skills("None");
        language.setName_language("None");
        course1.setCourse_name("Никакой курс не проходится");
        course1.setId_skills(skills);
        course1.setId_language(language);

        if(student.getId_group()==null){
            group.setCourse(course1);
            student.setId_group(group);
        }
        model.addAttribute(student);
        attributes.addFlashAttribute("student", student);
/*FindLastGroup*/
        List<Group> listLast = findLastGroup.findLastGroupsStudent(studentService, groupService, id);
        //List<Course> courses = courseService.getAllCourses();
        for (Group course : listLast) {
            course.setProgress(ThreadLocalRandom.current().nextInt(0, 101));
        }
        //model.addAttribute("courses", courses);
        model.addAttribute("last", listLast);


        return "StudentPersonal";
    }
    @GetMapping("/students/personal/{id}")
    public String change(@PathVariable("id") int id_stud, Model model){
        model.addAttribute("student", studentService.findById(id_stud));
        return "ChangePassword";
    }
    @PostMapping("/students/personal/{id}")
    public String changePassword(@PathVariable("id") int id,RedirectAttributes attributes, @RequestParam("lastPass") String lastPass, @RequestParam("newPass") String newPass, Model model){
        Student student = studentService.findById(id);
        User user = student.getId_user();
        if(BCrypt.checkpw(newPass, user.getPassword())){
            String err = "Вы ввели тот же пароль что и прошлый!";
            attributes.addFlashAttribute("err", err);
            model.addAttribute("err", err);
            attributes.addFlashAttribute("student", student);
            model.addAttribute("user", user);
            return "redirect:/students/personal/{id}";
        }
        else if (BCrypt.checkpw(lastPass, user.getPassword())){
            String err = "Это не ваш старый пароль!";
            attributes.addFlashAttribute("err", err);
            attributes.addFlashAttribute("err", err);
            model.addAttribute("err", err);
            attributes.addFlashAttribute("student", student);
            model.addAttribute("user", user);
            return "redirect:/students/personal/{id}";
        }else{
            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(newPass, salt);
            user.setPassword(hashedPassword);
            userService.save(user);
        }

        attributes.addFlashAttribute("student", student);
        return "redirect:/student/{id}";
    }
}
