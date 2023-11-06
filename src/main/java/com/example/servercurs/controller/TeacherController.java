package com.example.servercurs.controller;

import com.example.servercurs.Rating.RatingStudents;
import com.example.servercurs.entities.Group;
import com.example.servercurs.entities.Language;
import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.Teacher;
import com.example.servercurs.entities.User;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.LanguageService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequiredArgsConstructor
public class TeacherController {

     private final GroupService groupService;
     private final TeacherService teacherService;
     private final StudentService studentService;
     private final UserService userService;
     private final LanguageService languageService;


    RatingStudents ratingStudents = new RatingStudents();

    @GetMapping("/teacher/{id}")
    public String teacher(@PathVariable("id") int id,Model model,RedirectAttributes attributes){
        Teacher teacher = teacherService.findById(id);
        byte[] imageBytes = teacher.getId_user().getPhoto();

        // Кодирование изображения в base64
        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
        model.addAttribute("encodedImage", encodedImage);
        attributes.addFlashAttribute("encodedImage", encodedImage);
        List<Group> groupList = groupService.findGroupsByTeacher(id);
        for (Group course : groupList) {
            course.setProgress(ThreadLocalRandom.current().nextInt(0, 101));
        }
        model.addAttribute("groupList", groupList);
        attributes.addFlashAttribute("teacher", teacher);
        model.addAttribute("teacher", teacher);

        return "TeacherFirst";
    }

    @GetMapping("/teacher/gr/{id}")
    public String findGroups(@PathVariable int id,RedirectAttributes attributes, Model model){
        List<Group> groupList = groupService.findGroupsByTeacher(id);
        Teacher teacher = teacherService.findById(id);
        model.addAttribute("list",groupList);
        model.addAttribute("teacher",teacher );
        attributes.addFlashAttribute("teacher", teacher);

        return "findGroupsTeacher";
    }

    @GetMapping("/teacher/groups/{id}")
    public String checkAllGroups(@PathVariable(name = "id") int id,RedirectAttributes attributes, Model model){
        model.addAttribute("teacher", teacherService.findById(id));
        List<Group> listGr = groupService.findGroupsByTeacher(id);
        model.addAttribute("groups", listGr);
        attributes.addFlashAttribute("teacher", teacherService.findById(id));
        return "TeacherGroupRating";
    }
    @PostMapping("/teacher/groups/{id}")
    public String findGroup(@PathVariable(name="id") int id,RedirectAttributes attributes, @RequestParam("group") int groupId, Model model){
        List<Student> stud = studentService.findStudentByGroup(groupId);
        model.addAttribute("students", stud);
        if(stud.size()==0){
            String msg = "В данной группе отсутсвуют студенты";
            model.addAttribute("msg",msg);

        }
        List<Group> lst = groupService.findGroupsByTeacher(id);
        model.addAttribute("groups", lst);
        model.addAttribute("teacher", teacherService.findById(id));
        model.addAttribute("gr", groupService.findById(groupId));
        attributes.addFlashAttribute("teacher", teacherService.findById(id));

        return "TeacherGroupRating";
    }

    @PostMapping("/teacher/groups1/{id}/{id_student}/{id_group}")
    public String sendMarks(@PathVariable(name="id") int id,RedirectAttributes attributes,@PathVariable(name="id_student") int id_student,@PathVariable(name="id_group") int id_group, @RequestParam String rating, Model model){
        ratingStudents.checkRatingStudent(rating, id_student, studentService, groupService);
        model.addAttribute("teacher", teacherService.findById(id));
        List<Group> lst = groupService.findGroupsByTeacher(id);
        model.addAttribute("groups", lst);
        model.addAttribute("gr", groupService.findById(id_group));
        List<Student> stud = studentService.findStudentByGroup(id_group);
        model.addAttribute("students", stud);
        attributes.addFlashAttribute("teacher", teacherService.findById(id));
        return "TeacherGroupRating";
    }


    @GetMapping("/teacher/personal/{id}")
    public String change(@PathVariable("id") int id_stud, Model model,RedirectAttributes attributes){
        model.addAttribute("teacher", teacherService.findById(id_stud));
        attributes.addFlashAttribute("teacher", teacherService.findById(id_stud));

        return "ChangePasswordTeach";
    }

    @PostMapping("/teacher/personal/{id}")
    public String changePassword(@PathVariable("id") int id, RedirectAttributes attributes, @RequestParam("lastPass") String lastPass, @RequestParam("newPass") String newPass, Model model){
        Teacher teacher = teacherService.findById(id);
        User user = teacher.getId_user();
        if(BCrypt.checkpw(newPass, user.getPassword())){
            String err = "Вы ввели тот же пароль что и прошлый!";
            attributes.addFlashAttribute("err", err);
            model.addAttribute("err", err);
            attributes.addFlashAttribute("teacher", teacher);
            model.addAttribute("user", user);
            return "redirect:/teacher/personal/{id}";
        }
        else if (!BCrypt.checkpw(lastPass, user.getPassword())){
            String err = "Это не ваш старый пароль!";
            attributes.addFlashAttribute("err", err);
            //attributes.addFlashAttribute("err", err);
            model.addAttribute("err", err);
            attributes.addFlashAttribute("teacher", teacher);
            model.addAttribute("user", user);
            return "redirect:/teacher/personal/{id}";
        }else{
            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(newPass, salt);
            user.setPassword(hashedPassword);
            userService.save(user);
        }

        attributes.addFlashAttribute("teacher", teacher);
        return "redirect:/teacher/{id}";
    }

    @GetMapping("/teacher/personal/edit/{id}")
    public String edit(@PathVariable("id") int id,Model model, RedirectAttributes attributes){
        Teacher teacher = teacherService.findById(id);
        model.addAttribute("teacher", teacher);
        byte[] imageBytes = teacher.getId_user().getPhoto();

        // Кодирование изображения в base64
        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
        model.addAttribute("encodedImage", encodedImage);
        attributes.addFlashAttribute("encodedImage", encodedImage);
        attributes.addFlashAttribute("teacher", teacher);
        return "EditPersonalTeacher";
    }

    @PostMapping("/teacher/personal/edit/{id}")
    public String editPersonInformation(@PathVariable("id") int id, @RequestParam("surname") String surname, @RequestParam("name") String name,
                                        @RequestParam("mail") String mail, @RequestParam("photo") MultipartFile photo,@RequestParam("spec") String spec,
                                        @RequestParam("work") int work, RedirectAttributes attributes, Model model) throws IOException {
        Teacher teacher = teacherService.findById(id);
        User user = userService.findById(teacher.getId_user().getId_user());
        List<User> userList = userService.findAllUser();
        String encodedImage = null;

        if (photo.isEmpty()) {
            byte[] imageBytes = teacher.getId_user().getPhoto();

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
            teacher.setSpeciality(spec);
            teacher.setWork(work);
            userService.save(user);
            teacher.setId_user(user);
            teacherService.save(teacher);

        } else {
            user.setMail(mail);
            user.setSurname(surname);
            user.setName(name);
            teacher.setSpeciality(spec);
            teacher.setWork(work);
            int count = 0;
            for (User user1 : userList) {
                if (user1.getMail().equals(user.getMail())) {
                    count++;
                }
            }
            if (count == 0) {
                userService.save(user);
                teacher.setId_user(user);
                teacher.setSpeciality(spec);
                teacher.setWork(work);
                teacherService.save(teacher);
            } else {
                String error = "We have user with this mail.Enter another mail please!";
                attributes.addFlashAttribute("error", error);

                return "redirect:/teacher/personal/edit/{id}";
            }
        }

        model.addAttribute("encodedImage", encodedImage);
        attributes.addFlashAttribute("encodedImage", encodedImage);
        model.addAttribute("teacher",teacher);
        attributes.addFlashAttribute("teacher", teacher);
        return "redirect:/teacher/{id}";
    }

    @GetMapping("/teacher/groups/teach/{id}")
    public String startTech(@PathVariable("id") int id, RedirectAttributes attributes, Model model){
        Teacher teacher = teacherService.findById(id);
        List<Group> list = groupService.findAllGroups();
        List<Group> freeGroups = new ArrayList<>();
        for (Group gr:list) {
            if(gr.getTeacher()==null){
                freeGroups.add(gr);
            }
        }
        if(freeGroups.size()==0){
            String notFound = "У всех групп есть преподаватели, мы сообщим вам если появяться обновления";
            model.addAttribute("msg", notFound);
            attributes.addFlashAttribute("msg", notFound);
            return "redirect:/teacher/"+teacher.getId_teacher();
        }
        model.addAttribute("free",freeGroups);
        attributes.addFlashAttribute("free",freeGroups);
        model.addAttribute("teacher", teacher);
        attributes.addFlashAttribute("teacher", teacher);
        List<String> encodedImage = new ArrayList<>();
        List<Language> list1 = languageService.findAllLanguages();
        for (Group lg:list) {
            for(Language lang: list1) {
                String image = null;
                if (lg.getCourse().getId_language().getId_language()==lang.getId_language()) {
                    image = Base64.getEncoder().encodeToString(lang.getLogo());
                    encodedImage.add(image);
                }
                else {//encodedImage.put(lg.getName_language(), image);
                    continue;
                }
            }
        }
        model.addAttribute("encodedImage", encodedImage);
        attributes.addFlashAttribute("encodedImage", encodedImage);
        return "TeachCourse";
    }

    @PostMapping("/teacher/groups/teach/{id_teach}/{id_group}")
    public String startedTeach(@PathVariable("id_teach") int id_teach,@PathVariable("id_group") int id_group, RedirectAttributes attributes, Model model){
        Teacher teacher = teacherService.findById(id_teach);
        Group group1 = groupService.findById(id_group);
        group1.setTeacher(teacher);
        groupService.save(group1);
        List<Group> groupList = groupService.findGroupsByTeacher(id_teach);

        if(groupList.size()==0){
            String notFound = "У всех групп есть преподаватели, мы сообщим вам если появяться обновления";
            model.addAttribute("msg", notFound);
            attributes.addFlashAttribute("msg", notFound);
            return "redirect:/teacher"+teacher.getId_teacher();
        }
        model.addAttribute("list",groupList);
        model.addAttribute("teacher", teacher);
attributes.addFlashAttribute("teacher", teacher);

        return "redirect:/teacher/"+teacher.getId_teacher();
    }

    @GetMapping("/teacher/personal/delete/{id}")
    public String delete(@PathVariable("id") int id, Model model, RedirectAttributes attributes){
        Teacher teacher = teacherService.findById(id);
        teacher.setCheck("1");
        teacherService.save(teacher);
        model.addAttribute("teacher",teacher);
        attributes.addFlashAttribute("teacher", teacher);
        return "redirect:/teacher/{id}";
    }

}
