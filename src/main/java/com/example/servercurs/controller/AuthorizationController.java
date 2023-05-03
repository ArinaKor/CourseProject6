package com.example.servercurs.controller;

import com.example.servercurs.entities.*;
import com.example.servercurs.repository.*;
import com.example.servercurs.service.*;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class AuthorizationController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;

    static int stud;

    @GetMapping("/authorization")
    public String authorization(HttpServletResponse response){
        /*
        response.setHeader("Location", "/teacher");
        //response.sendRedirect("/teacher");*/
        return "authorization";
    }
    @PostMapping("/authorization")
    public String registration(RedirectAttributes attributes,@RequestParam String email, @RequestParam String password, Model model){
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(password, salt);
        User user = new User();/*
        Student student = new Student();
        */
        Role role = roleService.findById(3);
        user.setMail(email);
        user.setPassword(hashedPassword);
        user.setRole(role);
        Student student = new Student();
        List<User> userList = userService.findAllUser();
        //List<Group> group = groupRepository.findAll();
      //  System.out.println(group);

        int count = 0;
        for (User user1:userList) {
            if(user1.getMail().equals(user.getMail())){
                count++;
            }

        }
        if(count==0){
            userService.save(user);
            if(user.getRole().equals(role)){
                student.setId_user(user);
                studentService.save(student);
                model.addAttribute("student", student);

                attributes.addFlashAttribute("student", student);
                stud = student.getId_student();

            }
            /*student.get
            studentService.save()*/
        }
        else{
            String error="We have user with this mail.Enter another mail please!";
            model.addAttribute("error", error);
            return "authorization";
        }


        return "redirect:/student/{id}";
    }
    @PostMapping ("/authorization1")
    public String authorization(@RequestParam String email2, @RequestParam String pass, RedirectAttributes attributes, HttpServletResponse response, Model model) throws IOException {
        String salt = BCrypt.gensalt();
        List<User> list = userService.findAllUser();
        User user = new User();
        List<Role> roleList = roleService.findAllRoles();
        int c = 0;
        //String arina16 = BCrypt.hashpw("arina16", salt);
        //System.out.println(arina16);
        for (User user1:list) {
            if(user1.getMail().equals(email2)&&BCrypt.checkpw(pass, user1.getPassword())){
                user.setId_user(user1.getId_user());
                user.setName(user1.getName());
                user.setSurname(user1.getSurname());
                user.setMail(user1.getMail());
                user.setPassword(user1.getPassword());
                user.setRole(user1.getRole());
                c++;
            }
        }
        if (c == 0) {
            String err = "We haven't got this user!May be you want registration?";
            attributes.addFlashAttribute("err", err);

            return "redirect:/authorization";
        }
        if(user.getRole().equals(roleList.get(0))){

            return "redirect:/admin";
        }else if(user.getRole().equals(roleList.get(1))){
            Teacher teacher = teacherRepository.findTeacherById_user(user);
            model.addAttribute("teacher", teacher);
            attributes.addFlashAttribute("teacher", teacher);
/*

            response.setHeader("Location", "/teacher");
            //response.sendRedirect("/teacher");*/
            return "redirect:/teacher";
            /*return "redirect:/teacher";*/
        }else if(user.getRole().equals(roleList.get(2))){
            Student student = studentRepository.findStudentById_user(user);
            model.addAttribute("student", student);
            attributes.addFlashAttribute("student", student);
            stud = student.getId_student();
            return "redirect:/student/"+student.getId_student();
        }
        /*else if(!(user.getRole().equals(roleList.get(0))||user.getRole().equals(roleList.get(1))||user.getRole().equals(roleList.get(2)))){

            return "redirect:/authorization";
        }*//*
        String err = "We haven't got this user!May be you want registration?";
        model.addAttribute("err", err);*/
        return "authorization";
    }
    /*@GetMapping("/authorization1")
    public String findAllUsers(Model model){
        List<User> list = userService.findAllUser();
        model.addAttribute("list", list);
        return "AdminFirst";
    }*/

}
