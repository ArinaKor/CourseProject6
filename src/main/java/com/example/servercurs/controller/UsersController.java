package com.example.servercurs.controller;

import com.example.servercurs.Config.ConvertToByte;
import com.example.servercurs.entities.*;
import com.example.servercurs.repository.*;
import com.example.servercurs.service.*;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.*;

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
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
            private CourseRepository courseRepository;
   // private static final String API_KEY = "9d4a8d8c-7bb1-4267-891c-7f1c8bbd0509";

    ConvertToByte convertToByte = new ConvertToByte();
    @GetMapping("/admin")
    public String admin( Model model){
        User user = userRepository.findByRole("admin");
        model.addAttribute("user", user);
        byte[] imageBytes = user.getPhoto();

        // Кодирование изображения в base64
        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
        model.addAttribute("encodedImage", encodedImage);
        List<Object[]> courses = courseRepository.findGroupedCourses();
        Map<String, Long> groupedCourses = new HashMap<>();
        for (Object[] course : courses) {
            String direction = (String) course[0];
            Long count = (Long) course[1];
            groupedCourses.put(direction, count);
        }
        System.out.println(groupedCourses);
        model.addAttribute("map", groupedCourses);


        List<Course> courseList = courseRepository.findAll();
        List<Object[]> langs = courseRepository.findGroupedCoursesLang();
        model.addAttribute("data", langs);


        return "AdminFirst";
    }
    @GetMapping("/admin/users")
    public String findUsers(Model model){
        List<User> users = userService.findAllUser();
        List<User> list = new ArrayList<>();
        for (User user: users) {
            if(user.getRole().getId_role()!=1){
                list.add(user);
            }
        }
        model.addAttribute("users", list);
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
                         @RequestParam String roleName,@RequestParam String pass, @RequestParam String mail, Model model) throws IOException {
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(pass, salt);
        Role role = roleRepository.findRoleByRoleName(roleName);
        List<Role> roleCheck = roleService.findAllRoles();
        User user = new User();
        Teacher teacher = new Teacher();
        Student student = new Student();
        user.setSurname(surname);
        user.setName(name);
        user.setMail(mail);
        user.setPassword(hashedPassword);
        user.setRole(role);


        List<User> userList = userService.findAllUser();
        int count = 0;
        for (User user1:userList) {
            if(user1.getMail().equals(user.getMail())){
                count++;
            }

        }
        if(count==0){
            userService.save(user);
        }
        else{
            String error="We have user with this mail.Enter another mail please!";
            model.addAttribute("error", error);
            return "AddUser";
        }

            if(role.getRoleName().equals("student")){
                student.setId_user(user);
                user.setPhoto(convertToByte.convertImageToByteArray("D:\\unik\\sem6\\курсовой\\photo\\2.png"));
                userService.save(user);
                studentService.save(student);

            }
            else if(role.getRoleName().equals("teacher")) {
                teacher.setId_user(user);
                user.setPhoto(convertToByte.convertImageToByteArray("D:\\unik\\sem6\\курсовой\\photo\\3.png"));
                userService.save(user);
                teacherService.save(teacher);
            }


        return "redirect:/admin/users";
    }
    @PostMapping("/admin/users/{id_user}/edit")
    public String update(RedirectAttributes attributes, @PathVariable(value = "id_user") int id_user, @RequestParam String surname, @RequestParam String name,
                         @RequestParam String roleName, @RequestParam String mail, @RequestParam String rolee, Model model){

        Role role = roleRepository.findRoleByRoleName(roleName);

        String message = "takoe yzse est'";
        model.addAttribute("message", message);
        User user = userService.findById(id_user);
        List<Teacher> teacherList = teacherService.findAllTeachers();
        List<Student> studentList = studentService.findAllStudents();
        Teacher teacher = new Teacher();
        Student student = new Student();
        if(!user.getMail().equals(mail)){

            user.setSurname(surname);
        user.setName(name);
        user.setMail(mail);/*
        user.setPassword(pass);*/
        user.setRole(role);
        //userService.save(user);
        List<User> userList = userService.findAllUser();
        /*int count = 0;
        for (User user1:userList) {
            if(user1.getMail().equals(user.getMail())){
                count++;
            }

        }*/

            userService.save(user);
        }
        else{
            String error="We have user with this mail.Enter another mail please!";
            attributes.addFlashAttribute("error", error);
/*
            model.addAttribute()
*/

            return "redirect:/admin/users/{id_user}/edit";
        }
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

        return "redirect:/admin/users";
    }

    @GetMapping("/admin/checkDelete")
    public String deleteTeach(Model model, RedirectAttributes attributes){
        List<Teacher> deleteList = teacherRepository.findTeacherByCheck("1");
        if(deleteList.size()==0){
            String msg = "Никто увольняться не хочет!)";
            attributes.addFlashAttribute("msg",msg);
            return "redirect:/admin";
        }
        model.addAttribute("delete", deleteList);
        return "CheckApplications";
    }
    @PostMapping("/admin/checkDelete/faild/{id_user}")
    public String deleteFailed(@PathVariable("id_user") int id_user, Model model,RedirectAttributes redirectAttributes){
        User user = userService.findById(id_user);
        String mail = user.getMail();
        String body = "Уважаемый(-ая), "+ user.getSurname()+" "+user.getName()+"\nВаша заявка на увольнение была рассмотрена и отклонена.\nПо все вопросам просьба общаться к руководству.\n\nС уважением, Администрация IT Company Education Courses";
        String subject = "IT Company Education Courses";
        emailSenderService.sendSimpleEmail(mail, subject, body);
        Teacher teacher = teacherRepository.findTeacherById_user(user);
        teacher.setCheck("0");
        teacherService.save(teacher);
        return "redirect:/admin";
    }
    @PostMapping("/admin/checkDelete/success/{id_user}")
    public String deleteSuccess(@PathVariable("id_user") int id_user, Model model,RedirectAttributes redirectAttributes){
        User user = userService.findById(id_user);
        String mail = user.getMail();
        String body = "Уважаемый(-ая), "+ user.getSurname()+" "+user.getName()+"\nВаша заявка на увольнение была рассмотрена и одобрена.\nС данного момента доступ к рабочему аккаунту закрыт.\nПо все вопросам просьба общаться к руководству.\n\nС уважением, Администрация IT Company Education Courses";
        String subject = "IT Company Education Courses";
        emailSenderService.sendSimpleEmail(mail, subject, body);

        userService.delete(id_user);
        List<Teacher> deleteList = teacherRepository.findTeacherByCheck("1");
        //model.addAttribute("delete", deleteList);
        if(deleteList.size()==0){
            return "redirect:/admin";
        }
        return "CheckApplications";
    }
}
