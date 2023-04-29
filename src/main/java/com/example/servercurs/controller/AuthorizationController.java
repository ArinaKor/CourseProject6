package com.example.servercurs.controller;

import com.example.servercurs.entities.*;
import com.example.servercurs.repository.*;
import com.example.servercurs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private GroupRepository groupRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GroupService groupService;

    @GetMapping("/authorization")
    public String authorization(){
        return "authorization";
    }
    @PostMapping("/authorization")
    public String registration(@RequestParam String email, @RequestParam String password, Model model){

        User user = new User();/*
        Student student = new Student();
        */
        Role role = roleService.findById(3);
        user.setMail(email);
        user.setPassword(password);
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
            }
            /*student.get
            studentService.save()*/
        }
        else{
            String error="We have user with this mail.Enter another mail please!";
            model.addAttribute("error", error);
            return "authorization";
        }


        return "StudentFirst";
    }
    @PostMapping ("/authorization1")
    public String authorization(@RequestParam String email2, @RequestParam String pass, Model model){

        List<User> list = userService.findAllUser();
        User user = new User();
        List<Role> roleList = roleService.findAllRoles();
        for (User user1:list) {
            if(user1.getMail().equals(email2)&&user1.getPassword().equals(pass)){
                user.setId_user(user1.getId_user());
                user.setName(user1.getName());
                user.setSurname(user1.getSurname());
                user.setMail(user1.getMail());
                user.setPassword(user1.getPassword());
                user.setRole(user1.getRole());
            }
        }
        if(user.getRole().equals(roleList.get(0))){

            return "AdminFirst";
        }else if(user.getRole().equals(roleList.get(1))){
            Teacher teacher = teacherRepository.findTeacherById_user(user);

            model.addAttribute("teacher", teacher);
            return "TeacherFirst";
        }else if(user.getRole().equals(roleList.get(2))){
            /*List<Student> list1 = studentService.findAllStudents();
            model.addAttribute("list", list1);*/
            Student student = studentRepository.findStudentById_user(user);
            model.addAttribute("student", student);
            List<Group> listCourse = groupService.findAllGroups();
            model.addAttribute("list", listCourse);

            int k=0;
            if(student.getId_group()==null){
                k=0;
            }
           else if(!(student.getId_group()==null)){
                k++;
            }
            System.out.println(k);
            model.addAttribute("k", k);

            return "StudentFirst";
        }
        String err = "We haven't got this user!May be you want registration?";
        model.addAttribute("err", err);
        return "authorization";
    }
    /*@GetMapping("/authorization1")
    public String findAllUsers(Model model){
        List<User> list = userService.findAllUser();
        model.addAttribute("list", list);
        return "AdminFirst";
    }*/

}
