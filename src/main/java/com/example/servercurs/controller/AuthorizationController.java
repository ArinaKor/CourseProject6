package com.example.servercurs.controller;

import com.example.servercurs.Config.ConvertToByte;

import com.example.servercurs.entities.Role;
import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.Teacher;
import com.example.servercurs.entities.User;
import com.example.servercurs.repository.StudentRepository;
import com.example.servercurs.repository.TeacherRepository;
import com.example.servercurs.service.RoleService;
import com.example.servercurs.service.StudentService;
import com.example.servercurs.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorizationController {

    private final UserService userService;
    private final RoleService roleService;
    private final StudentService studentService;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    ConvertToByte convertToByte = new ConvertToByte();

    static int stud;
    @GetMapping("/")
    public String index(){
        return "authorization";
    }

    @GetMapping("/authorization")
    public String authorization(HttpServletResponse response){
        return "authorization";
    }

    @PostMapping("/authorization")
    public String registration(RedirectAttributes attributes,@RequestParam String email, @RequestParam String password, Model model) throws IOException {
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(password, salt);
        User user = new User();
        Role role = roleService.findById(3);
        user.setMail(email);
        user.setPassword(hashedPassword);
        user.setRole(role);
        user.setPhoto(convertToByte.convertImageToByteArray("D:\\unik\\sem6\\курсовой\\photo\\2.png"));
        Student student = new Student();
        List<User> userList = userService.findAllUser();
        int count = 0;
        for (User user1:userList) {
            if(user1.getMail().equals(user.getMail())){
                count++;
            }

        }
        if(count==0){
            userService.save(user);
            if(user.getRole().equals(role)){
                user.setSurname("Surname");
                user.setName("Name");
                student.setId_user(user);
                student.setCourses("0,");
                studentService.save(student);
                model.addAttribute("student", student);

                attributes.addFlashAttribute("student", student);
                stud = student.getId_student();

            }
        }
        else{
            String error="We have user with this mail.Enter another mail please!";
            model.addAttribute("error", error);
            return "authorization";
        }


        return "redirect:/student/"+student.getId_student();
    }
    @PostMapping ("/authorization1")
    public String authorization(@RequestParam String email2, @RequestParam String pass, RedirectAttributes attributes, HttpServletResponse response, Model model) throws IOException {
        String salt = BCrypt.gensalt();
        List<User> list = userService.findAllUser();
        User user = new User();
        List<Role> roleList = roleService.findAllRoles();
        int c = 0;
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
            teacher.setCheck("0");
            model.addAttribute("teacher", teacher);
            attributes.addFlashAttribute("teacher", teacher);
            return "redirect:/teacher/"+teacher.getId_teacher();
        }else if(user.getRole().equals(roleList.get(2))){
            Student student = studentRepository.findStudentById_user(user);
            model.addAttribute("student", student);
            attributes.addFlashAttribute("student", student);
            stud = student.getId_student();
            return "redirect:/student/"+student.getId_student();
        }
        return "authorization";
    }


}
