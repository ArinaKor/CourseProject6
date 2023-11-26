package com.example.servercurs.controller;

import com.example.servercurs.Certificate.FindLastGroup;
import com.example.servercurs.Config.ConvertToByte;
import com.example.servercurs.entities.Course;
import com.example.servercurs.entities.CourseLesson;
import com.example.servercurs.entities.Group;
import com.example.servercurs.entities.Language;
import com.example.servercurs.entities.LessonsHistory;
import com.example.servercurs.entities.Notification;
import com.example.servercurs.entities.Skills;
import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.User;
import com.example.servercurs.service.CourseLessonService;
import com.example.servercurs.service.EmailSenderService;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.LessonsHistoryService;
import com.example.servercurs.service.NotificationService;
import com.example.servercurs.service.StudentService;
import com.example.servercurs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequiredArgsConstructor
public class StudentPersonalController {

    private final StudentService studentService;
    private final GroupService groupService;
    private final UserService userService;
    private final EmailSenderService emailSenderService;
    private final NotificationService notificationService;
    private final LessonsHistoryService lessonsHistoryService;
    private final CourseLessonService courseLessonService;


    FindLastGroup findLastGroup = new FindLastGroup();

    @GetMapping("/student/{id}")
    public String checkPersonalPage(@PathVariable(name = "id") int id, RedirectAttributes attributes, Model model) {
        Student student = studentService.findById(id);

        Group group = new Group();
        Course course1 = new Course();
        Skills skills = new Skills();
        Language language = new Language();
        byte[] imageBytes = student.getId_user().getPhoto();

        // Кодирование изображения в base64
        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
        model.addAttribute("encodedImage", encodedImage);
        attributes.addFlashAttribute("encodedImage", encodedImage);

        skills.setName_skills("None");
        language.setName_language("None");
        course1.setCourse_name("Никакой курс не проходится");
        course1.setId_skills(skills);
        course1.setId_language(language);

        if (student.getId_group() == null) {
            group.setCourse(course1);
            student.setId_group(group);
        }
        model.addAttribute(student);
        attributes.addFlashAttribute("student", student);
        List<Group> listLast = findLastGroup.findLastGroupsStudent(studentService, groupService, id);
        List<LessonsHistory> history = lessonsHistoryService.findByStudentAndCourse(student.getId_student());
        List<CourseLesson> lessons = courseLessonService.findByCourse(student.getId_group().getCourse().getId_course());
        for (Group course : listLast) {
            //так как нет курсов у студента, падает ошибка, надо брать из списка прошедших курсов курсов)
            course.setProgress((double) history.size() / lessons.size() * 100);

        }
        model.addAttribute("last", listLast);
        User user = userService.findById(student.getId_user().getId_user());
        List<Notification> notifications = notificationService.findById_userAndAndCheckNotificationTrue();
        model.addAttribute("notifications", notifications);
        return "StudentPersonal";
    }

    @GetMapping("/students/personal/{id}")
    public String change(@PathVariable("id") int id_stud, Model model, RedirectAttributes attributes) {
        model.addAttribute("student", studentService.findById(id_stud));
        attributes.addFlashAttribute("student", studentService.findById(id_stud));

        return "ChangePassword";
    }

    @PostMapping("/students/personal/{id}")
    public String changePassword(@PathVariable("id") int id, RedirectAttributes attributes, @RequestParam("lastPass") String lastPass, @RequestParam("newPass") String newPass, Model model) {
        Student student = studentService.findById(id);
        User user = student.getId_user();
        if (BCrypt.checkpw(newPass, user.getPassword())) {
            String err = "Вы ввели тот же пароль что и прошлый!";
            attributes.addFlashAttribute("err", err);
            model.addAttribute("err", err);
            attributes.addFlashAttribute("student", student);
            model.addAttribute("user", user);
            return "redirect:/students/personal/{id}";
        } else if (!BCrypt.checkpw(lastPass, user.getPassword())) {
            String err = "Это не ваш старый пароль!";
            attributes.addFlashAttribute("err", err);
            model.addAttribute("err", err);
            attributes.addFlashAttribute("student", student);
            model.addAttribute("user", user);
            return "redirect:/students/personal/{id}";
        } else {
            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(newPass, salt);
            user.setPassword(hashedPassword);
            userService.save(user);
        }

        attributes.addFlashAttribute("student", student);
        return "redirect:/student/{id}";
    }

    @GetMapping("/students/personal/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model, RedirectAttributes attributes) {
        Student student = studentService.findById(id);

        byte[] imageBytes = student.getId_user().getPhoto();

        // Кодирование изображения в base64
        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
        model.addAttribute("encodedImage", encodedImage);
        attributes.addFlashAttribute("encodedImage", encodedImage);
        if (student.getId_group() == null) {
            Course course = new Course();
            Skills skills = new Skills();
            Language language = new Language();
            Group group = new Group();
            skills.setName_skills("None");
            language.setName_language("None");
            course.setCourse_name("Никакой курс не проходится");
            course.setId_skills(skills);
            course.setId_language(language);
            group.setCourse(course);
            student.setId_group(group);

        }
        model.addAttribute("student", student);
        return "EditPersonalStudent";
    }

    @PostMapping("/students/personal/edit/{id}")
    public String editPersonInformation(@PathVariable("id") int id, @RequestParam("surname") String surname, @RequestParam("name") String name,
                                        @RequestParam("mail") String mail, @RequestParam("photo") MultipartFile photo, RedirectAttributes attributes, Model model) throws IOException {
        Student student = studentService.findById(id);
        User user = userService.findById(student.getId_user().getId_user());
        List<User> userList = userService.findAllUser();
        String encodedImage = null;
        if (photo.isEmpty()) {
            byte[] imageBytes = student.getId_user().getPhoto();

            // Кодирование изображения в base64
            encodedImage = Base64.getEncoder().encodeToString(imageBytes);
            user.setPhoto(imageBytes);
            userService.save(user);
            model.addAttribute("encodedImage", encodedImage);
            attributes.addFlashAttribute("encodedImage", encodedImage);

        } else {
            user.setPhoto(photo.getBytes());
        }
        if (user.getMail().equals(mail)) {
            //user.setMail(mail);
            user.setSurname(surname);
            user.setName(name);
            // user.setPhoto(photo.getBytes());
            userService.save(user);
            student.setId_user(user);
            studentService.save(student);

        } else {
            user.setMail(mail);
            user.setSurname(surname);
            user.setName(name);
            int count = 0;
            for (User user1 : userList) {
                if (user1.getMail().equals(user.getMail())) {
                    count++;
                }
            }
            if (count == 0) {
                userService.save(user);
                student.setId_user(user);
                studentService.save(student);
            } else {
                String error = "We have user with this mail.Enter another mail please!";
                attributes.addFlashAttribute("error", error);

                return "redirect:/students/personal/edit/{id}";
            }
        }

        List<Group> listLast = findLastGroup.findLastGroupsStudent(studentService, groupService, id);
        for (Group course : listLast) {
            course.setProgress(ThreadLocalRandom.current().nextInt(0, 101));
        }
        model.addAttribute("last", listLast);
        model.addAttribute("student", student);
        attributes.addFlashAttribute("student", student);
        model.addAttribute("encodedImage", encodedImage);
        attributes.addFlashAttribute("encodedImage", encodedImage);

        return "redirect:/student/{id}";
    }

    @GetMapping("/sendCode/{id}")
    public String send(@PathVariable("id") int id, Model model) {
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        return "AddMoney";
    }

    @PostMapping("/sendCode/mail/{id}")
    public String sendCode(@PathVariable("id") int id, @RequestParam String money, RedirectAttributes attributes, Model model) {

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
        String body = "Для подтверждения вашей личности мы высылаем данный код\nВаш код: " + codeMail + "\nУбедительная просьба" +
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

    @PostMapping("/sendMail/checkCode/{id}")
    public String checkCode(@PathVariable("id") int id_student, @RequestParam String money, @RequestParam String code, @RequestParam String codeMail, Model model, RedirectAttributes attributes) {
        Student student = studentService.findById(id_student);
        if (codeMail.equals(code)) {
            student.setBalance(student.getBalance() + Double.parseDouble(money));
            studentService.save(student);
            attributes.addFlashAttribute("student", student);
            return "redirect:/student/" + student.getId_student();
        } else {
            String err = "Error code";
            model.addAttribute("err", err);
            model.addAttribute("student", student);
            return "AddMoney";
        }
    }


}
