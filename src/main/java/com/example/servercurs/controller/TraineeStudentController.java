package com.example.servercurs.controller;

import com.example.servercurs.entities.Notification;
import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.User;
import com.example.servercurs.service.CourseService;
import com.example.servercurs.service.NotificationService;
import com.example.servercurs.service.StudentService;
import com.example.servercurs.service.TraineeService;
import com.example.servercurs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student/trainees/")
public class TraineeStudentController {
    private final TraineeService traineeService;
    private final StudentService studentService;
    private final CourseService courseService;


    @GetMapping("/{id_student}")
    private String findAllGroups(@PathVariable(name = "id_student") int id_student, Model model){
        Student student = studentService.findById(id_student);
        model.addAttribute("student", student);
        model.addAttribute("trainees", traineeService.findAll());
        List<String> encodedImage = courseService.encodedImageForTrainee();
        model.addAttribute("encodedImage", encodedImage);

   /*     User user = userService.findById(student.getId_user().getId_user());
        List<Notification> notifications = notificationService.findById_userAndAndCheckNotificationTrue();
        model.addAttribute("notifications", notifications);*/

        return "StudentTrainees";
    }

    @PostMapping("/{id_student}/reply")
    public String replyStudentTrainee( @PathVariable(name = "id_student") int studentId, RedirectAttributes attributes){
        traineeService.replyTrainee( studentId);
        Student student = studentService.findById(studentId);
        attributes.addFlashAttribute("student", student);

        return "redirect:/student/trainees/" + student.getId_student();
    }



}
