package com.example.servercurs.controller;

import com.example.servercurs.Config.ConvertToByte;
import com.example.servercurs.entities.Course;
import com.example.servercurs.entities.Role;
import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.Teacher;
import com.example.servercurs.entities.User;
import com.example.servercurs.service.CourseService;
import com.example.servercurs.service.RoleService;
import com.example.servercurs.service.StudentService;
import com.example.servercurs.service.TeacherService;
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
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AuthorizationController {

    private final UserService userService;
    private final RoleService roleService;
    private final StudentService studentService;
    private final CourseService courseService;
    private final TeacherService teacherService;

    ConvertToByte convertToByte = new ConvertToByte();

    static int stud;

    @GetMapping("/auth")
    public String auth(){
        return "authorization";
    }
    @GetMapping("/")
    public String index(){
        return "LandingPage";
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
            Teacher teacher = teacherService.findTeacherById_user(user);
            teacher.setCheck("0");
            model.addAttribute("teacher", teacher);
            attributes.addFlashAttribute("teacher", teacher);
            return "redirect:/teacher/"+teacher.getId_teacher();
        }else if(user.getRole().equals(roleList.get(2))){
            Student student = studentService.findStudentById_user(user);
            model.addAttribute("student", student);
            attributes.addFlashAttribute("student", student);
            stud = student.getId_student();
            return "redirect:/student/"+student.getId_student();
        }
        return "authorization";
    }

    @GetMapping("/student")
    private String student(RedirectAttributes attributes, Model model) {
        return "StudentPersonal";
    }

    @GetMapping("/admin")
    public String admin( Model model){
        User user = userService.findByRoleAdmin("admin");
        model.addAttribute("user", user);
        byte[] imageBytes = user.getPhoto();

        // Кодирование изображения в base64
        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
        model.addAttribute("encodedImage", encodedImage);
        List<Object[]> courses = courseService.findGroupedCourses();
        Map<String, Long> groupedCourses = new HashMap<>();
        for (Object[] course : courses) {
            String direction = (String) course[0];
            Long count = (Long) course[1];
            groupedCourses.put(direction, count);
        }
        System.out.println(groupedCourses);
        model.addAttribute("map", groupedCourses);

        List<Course> courseList = courseService.findAllCourse();
        List<Object[]> langs = courseService.findGroupedCoursesLang();
        model.addAttribute("data", langs);


        return "AdminFirst";
    }


}
