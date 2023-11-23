package com.example.servercurs.controller;

import com.example.servercurs.Config.ConvertToByte;
import com.example.servercurs.entities.Role;
import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.Teacher;
import com.example.servercurs.entities.User;
import com.example.servercurs.service.RoleService;
import com.example.servercurs.service.StudentService;
import com.example.servercurs.service.TeacherService;
import com.example.servercurs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UsersController {

    private final UserService userService;
    private final RoleService roleService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    ConvertToByte convertToByte = new ConvertToByte();

    @GetMapping
    public String findUsers(Model model) {

        List<User> list = userService.findUsersByRole();
        model.addAttribute("users", list);
        return "AdminUsers";
    }

    @PostMapping("/{id_user}/delete")
    public String deleteUser(@PathVariable(name = "id_user") int id_user, Model model) {
        userService.delete(id_user);
        return "redirect:/admin/users";
    }

    @GetMapping("/{id_user}/edit")
    public String edit(@PathVariable(value = "id_user") int id_user, Model model) {
        User user = userService.findById(id_user);
        model.addAttribute("el", user);
        List<Role> lastRoles = userService.edit(id_user);
        model.addAttribute("role", lastRoles);
        return "EditUser";
    }

    @GetMapping("/add")
    public String add(Model model) {
        List<Role> lastRoles = userService.add();
        model.addAttribute("role", lastRoles);
        return "AddUser";
    }

    @PostMapping("/add")
    public String addUser(@RequestParam String surname, @RequestParam String name,
                          @RequestParam String roleName, @RequestParam String pass, @RequestParam String mail, Model model) throws IOException {
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(pass, salt);
        Role role = roleService.findRoleByRoleName(roleName);
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
        for (User user1 : userList) {
            if (user1.getMail().equals(user.getMail())) {
                count++;
            }

        }
        if (count == 0) {
            userService.save(user);
        }
        if (count != 0) {
            String error = "We have user with this mail.Enter another mail please!";
            model.addAttribute("error", error);
            return "AddUser";
        }
        if (role.getRoleName().equals("student")) {
            student.setId_user(user);
            user.setPhoto(convertToByte.convertImageToByteArray("D:\\unik\\sem6\\курсовой\\photo\\2.png"));
            userService.save(user);
            studentService.save(student);

        } else if (role.getRoleName().equals("teacher")) {
            teacher.setId_user(user);
            user.setPhoto(convertToByte.convertImageToByteArray("D:\\unik\\sem6\\курсовой\\photo\\3.png"));
            userService.save(user);
            teacherService.save(teacher);
        }


        return "redirect:/admin/users";
    }

    @PostMapping("/{id_user}/edit")
    public String update(RedirectAttributes attributes, @PathVariable(value = "id_user") int id_user, @RequestParam String surname, @RequestParam String name,
                         @RequestParam String mail, @RequestParam String rolee, Model model){

        Role role = roleService.findRoleByRoleName(rolee);
        String message = "takoe yzse est'";
        model.addAttribute("message", message);
        User user = userService.findById(id_user);

        if(!user.getMail().equals(mail)){
            user.setSurname(surname);
            user.setName(name);
            user.setMail(mail);
            user.setRole(role);
            userService.save(user);
        }
        else{
            String error="We have user with this mail.Enter another mail please!";
            attributes.addFlashAttribute("error", error);
            return "redirect:/admin/users/{id_user}/edit";
        }
        return "redirect:/admin/users";
    }

}
