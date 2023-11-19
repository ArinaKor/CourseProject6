package com.example.servercurs.controller;

import com.example.servercurs.entities.Teacher;
import com.example.servercurs.entities.User;
import com.example.servercurs.repository.TeacherRepository;
import com.example.servercurs.service.EmailSenderService;
import com.example.servercurs.service.GoogleSheetsService;
import com.example.servercurs.service.TeacherService;
import com.example.servercurs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/checkDelete")
public class TeacherDeletedController {

    private final UserService userService;
    private final TeacherService teacherService;
    private final TeacherRepository teacherRepository;
    private final EmailSenderService emailSenderService;
    private final GoogleSheetsService googleSheetsService;

    @GetMapping
    public String deleteTeach(Model model, RedirectAttributes attributes) throws GeneralSecurityException, IOException {
        List<Teacher> deleteList = teacherRepository.findTeacherByCheck("1");
        googleSheetsService.saveTraineeReply();
        if(deleteList.size()==0){
            String msg = "Никто увольняться не хочет!)";
            attributes.addFlashAttribute("msg",msg);
            return "redirect:/admin";
        }
        model.addAttribute("delete", deleteList);

        return "CheckApplications";
    }

    @PostMapping("/faild/{id_user}")
    public String deleteFailed(@PathVariable("id_user") int id_user, Model model, RedirectAttributes redirectAttributes){
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

    @PostMapping("/success/{id_user}")
    public String deleteSuccess(@PathVariable("id_user") int id_user, Model model,RedirectAttributes redirectAttributes){
        User user = userService.findById(id_user);
        String mail = user.getMail();
        String body = "Уважаемый(-ая), "+ user.getSurname()+" "+user.getName()+"\nВаша заявка на увольнение была рассмотрена и одобрена.\nС данного момента доступ к рабочему аккаунту закрыт.\nПо все вопросам просьба общаться к руководству.\n\nС уважением, Администрация IT Company Education Courses";
        String subject = "IT Company Education Courses";
        emailSenderService.sendSimpleEmail(mail, subject, body);
        userService.delete(id_user);
        List<Teacher> deleteList = teacherRepository.findTeacherByCheck("1");
        if(deleteList.size()==0){
            return "redirect:/admin";
        }
        return "CheckApplications";
    }
}
