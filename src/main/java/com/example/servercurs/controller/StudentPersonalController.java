package com.example.servercurs.controller;

import com.example.servercurs.Certificate.FindLastGroup;
import com.example.servercurs.entities.*;
import com.example.servercurs.service.EmailSenderService;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.StudentService;
import com.example.servercurs.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class StudentPersonalController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSenderService emailSenderService;
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
    public String change(@PathVariable("id") int id_stud, Model model, RedirectAttributes attributes){
        model.addAttribute("student", studentService.findById(id_stud));
        attributes.addFlashAttribute("student", studentService.findById(id_stud));

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
           // attributes.addFlashAttribute("err", err);
            attributes.addFlashAttribute("err", err);
            model.addAttribute("err", err);
            attributes.addFlashAttribute("student", student);
            model.addAttribute("user", user);
            attributes.addFlashAttribute("user", user);
            model.addAttribute("student", student);
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

    @GetMapping("/students/personal/edit/{id}")
    public String edit(@PathVariable("id") int id,Model model){
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        return "EditPersonalStudent";
    }
    @PostMapping("/students/personal/edit/{id}")
    public String editPersonInformation(@PathVariable("id") int id,@RequestParam("surname") String surname, @RequestParam("name") String name,
                                        @RequestParam("mail") String mail, RedirectAttributes attributes, Model model){
        Student student = studentService.findById(id);
        User user = userService.findById(student.getId_user().getId_user());
        List<User> userList = userService.findAllUser();
        if(user.getMail().equals(mail)){
            //user.setMail(mail);
            user.setSurname(surname);
            user.setName(name);
            userService.save(user);
            student.setId_user(user);
            studentService.save(student);

        }
        else{
            user.setMail(mail);
            user.setSurname(surname);
            user.setName(name);
            int count = 0;
            for (User user1:userList) {
                if(user1.getMail().equals(user.getMail())){
                    count++;
                }
            }
            if(count==0){
                userService.save(user);
                student.setId_user(user);
                studentService.save(student);
            }
            else{
                String error="We have user with this mail.Enter another mail please!";
                attributes.addFlashAttribute("error", error);

                return "redirect:/students/personal/edit/{id}";
            }
        }

        List<Group> listLast = findLastGroup.findLastGroupsStudent(studentService, groupService, id);
        for (Group course : listLast) {
            course.setProgress(ThreadLocalRandom.current().nextInt(0, 101));
        }
        //model.addAttribute("courses", courses);
        model.addAttribute("last", listLast);
        model.addAttribute("student",student);
        return "StudentPersonal";
    }

    @GetMapping("/sendCode/{id}")
    public String send(@PathVariable("id") int id, Model model){
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        return "AddMoney";
    }

    @PostMapping("/sendCode/mail/{id}")
    //@ResponseBody
    public String sendCode(@PathVariable("id") int id,@RequestParam String money, RedirectAttributes attributes,Model model){

        Student student = studentService.findById(id);
        String codeMail = "";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 6;
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            codeMail += characters.charAt(index);
        }
        double balance = Double.parseDouble(money);

        String mail = student.getId_user().getMail();
        String body = "Для подтверждения вашей личности мы высылаем данный код\nВаш код: "+codeMail+"\nУбедительная просьба" +
                " не разглашайте данный код и не отвечайне на данное сообщение если запрос сделали не вы\n" +
                "С уважением,IT Company Education Courses";
        String subject = "IT Company Education Courses";
        emailSenderService.sendSimpleEmail(mail, subject, body);
        attributes.addFlashAttribute("student", student);
        model.addAttribute("student", student);
        model.addAttribute("money", money);
        model.addAttribute("code", codeMail);
        model.addAttribute("showCodeInput", true);

        //return "AddMoney";
        return "AddMoney";
    }
    /*@GetMapping("/sendMail/checkCode/{id}")
    public String checkPage(@PathVariable("id") int id, RedirectAttributes redirectAttributes, Model model){

        return "writeCode";
    }*/

    @PostMapping("/sendMail/checkCode/{id}")
    //@ResponseBody
    public String checkCode(@PathVariable("id") int id_student, @RequestParam String money, @RequestParam String code, @RequestParam String codeMail,Model model ,RedirectAttributes attributes){
        Student student = studentService.findById(id_student);
        if(codeMail.equals(code)){
            student.setBalance(student.getBalance()+Double.parseDouble(money));
            studentService.save(student);
            attributes.addFlashAttribute("student", student);
            return "redirect:/student/"+student.getId_student();
        }else{
            String err="Error code";
            model.addAttribute("err", err);
            model.addAttribute("student", student);
            return "AddMoney";
        }

        //return "";
    }

}
