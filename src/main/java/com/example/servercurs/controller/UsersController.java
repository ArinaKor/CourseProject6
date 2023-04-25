package com.example.servercurs.controller;

import com.example.servercurs.entities.*;
import com.example.servercurs.repository.RoleRepository;
import com.example.servercurs.repository.StudentRepository;
import com.example.servercurs.repository.TeacherRepository;
import com.example.servercurs.service.RoleService;
import com.example.servercurs.service.StudentService;
import com.example.servercurs.service.TeacherService;
import com.example.servercurs.service.UserService;
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
public class UsersController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/admin/users")
    public String findUsers(Model model){
        List<User> users = userService.findAllUser();
        model.addAttribute("users", users);
        return "AdminUsers";
    }
    @PostMapping("/admin/users/{id_user}/delete")
    public String deleteUser(@PathVariable(name = "id_user") int id_user, Model model){
        userService.delete(id_user);
        return "redirect:/admin/users";
    }
    @GetMapping("/admin/users/{id_user}/edit")
    public String edit(@PathVariable(value = "id_user") int id_user, Model model){
        User user = userService.findById(id_user);
        model.addAttribute("el", user);
        List<Role> roleList = roleService.findAllRoles();
        List<Role> lastRoles = new ArrayList<>();
        for (Role rl:roleList) {
            if(rl.getRoleName().equals("admin")){
                continue;
            }
            else{
                lastRoles.add(rl);
            }

        }
        /*String message = "takoe yzse est'";
        model.addAttribute("message", message);
        List<User> userList = userService.findAllUser();
        model.addAttribute("users", userList);*/
        model.addAttribute("role", lastRoles);
        return "EditUser";
    }
    @PostMapping("/admin/users/{id_user}/edit")
    public String update(@PathVariable(value = "id_user") int id_user, @RequestParam String surname, @RequestParam String name,
                         @RequestParam String roleName, @RequestParam String mail,@RequestParam String rolee, Model model){

        Role role = roleRepository.findRoleByRoleName(roleName);

        String message = "takoe yzse est'";
        model.addAttribute("message", message);
        User user = userService.findById(id_user);
        List<Teacher> teacherList = teacherService.findAllTeachers();
        List<Student> studentList = studentService.findAllStudents();
        Teacher teacher = new Teacher();
        Student student = new Student();
        user.setSurname(surname);
        user.setName(name);
        user.setMail(mail);/*
        user.setPassword(pass);*/
        user.setRole(role);
        userService.save(user);
        if(!roleName.equals(rolee)){
            if(rolee.equals("student")){
                for (Student st:studentList) {
                    if(st.getId_user().equals(user)){
                        teacher.setId_user(user);
                       /* teacherService.save(teacher);*/

                        teacherService.save(teacher);
                        studentService.delete(st.getId_student());
                        break;
                    }

                } /*
                */
            }
            if(rolee.equals("teacher")){
                for (Teacher tc:teacherList) {
                    if(tc.getId_user().equals(user)){
                        student.setId_user(user);
                        studentService.save(student);
                        teacherService.delete(tc.getId_teacher());
                        /*student.setId_user(user);
                        studentService.save(student);*/
                        break;
                    }
                }
            }
        }
        userService.save(user);
        /*if(!role.getRoleName().equals(role2.getRoleName())){
            if(user.getRole().getRoleName().equals("student")){
                Teacher teacher = teacherRepository.deleteTeacherById_user(user);

                teacherRepository.deleteById(teacher.getId_teacher());
                stud.setId_user(user);
                studentService.save(stud);

            }
            else if(user.getRole().getRoleName().equals("teacher")) {
                Student student = studentRepository.deleteStudentById_user(user);

                teach.setId_user(user);
                teacherService.save(teach);
            }
        }*/

        return "redirect:/admin/users";
    }
    @GetMapping("/admin/users/add")
    public String add( Model model){
        List<Role> roleList = roleService.findAllRoles();
        List<Role> lastRoles = new ArrayList<>();
        for (Role rl:roleList) {
            if(rl.getRoleName().equals("admin")){
                continue;
            }
            else{
                lastRoles.add(rl);
            }

        }
        model.addAttribute("role", lastRoles);
        return "AddUser";
    }
    @PostMapping("/admin/users/add")
    public String addUser( @RequestParam String surname, @RequestParam String name,
                         @RequestParam String roleName,@RequestParam String pass, @RequestParam String mail, Model model){
        Role role = roleRepository.findRoleByRoleName(roleName);
        List<Role> roleCheck = roleService.findAllRoles();
        User user = new User();
        Teacher teacher = new Teacher();
        Student student = new Student();
        user.setSurname(surname);
        user.setName(name);
        user.setMail(mail);
        user.setPassword(pass);
        user.setRole(role);
        userService.save(user);

            if(role.getRoleName().equals("student")){
                student.setId_user(user);
                studentService.save(student);

            }
            else if(role.getRoleName().equals("teacher")) {
                teacher.setId_user(user);
                teacherService.save(teacher);
            }


        return "redirect:/admin/users";
    }
}
