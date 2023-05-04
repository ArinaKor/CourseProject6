package com.example.servercurs.controller;

import com.example.servercurs.Certificate.FindLastGroup;
import com.example.servercurs.Rating.RatingStudents;
import com.example.servercurs.entities.Group;
import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.Teacher;
import com.example.servercurs.entities.User;
import com.example.servercurs.repository.GroupRepository;
import com.example.servercurs.repository.StudentRepository;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.StudentService;
import com.example.servercurs.service.TeacherService;
import com.example.servercurs.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.ws.rs.core.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class TeacherController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
            private UserService userService;

    FindLastGroup findLastGroup = new FindLastGroup();

    RatingStudents ratingStudents = new RatingStudents();
    List<Integer> deleteTeachers = new ArrayList<>();

    @GetMapping("/teacher/{id}")
    public String teacher(@PathVariable("id") int id,Model model,RedirectAttributes attributes){
        model.addAttribute("teacher", teacherService.findById(id));
        List<Group> groupList = groupRepository.findGroupsByTeacher(id);
        for (Group course : groupList) {
            course.setProgress(ThreadLocalRandom.current().nextInt(0, 101));
        }
        model.addAttribute("groupList", groupList);
        attributes.addFlashAttribute("teacher", teacherService.findById(id));

        return "TeacherFirst";
    }
    @GetMapping("/teacher/gr/{id}")
    public String findGroups(@PathVariable int id, Model model){
        List<Group> groupList = groupRepository.findGroupsByTeacher(id);

        model.addAttribute("list",groupList);
        model.addAttribute("teacher", teacherService.findById(id));

        return "findGroupsTeacher";
    }
    @GetMapping("/teacher/groups/{id}")
    public String checkAllGroups(@PathVariable(name = "id") int id, Model model){
        model.addAttribute("teacher", teacherService.findById(id));
        List<Group> listGr = groupRepository.findGroupsByTeacher(id);
        model.addAttribute("groups", listGr);
        return "TeacherGroupRating";
    }
    @PostMapping("/teacher/groups/{id}")
    public String findGroup(@PathVariable(name="id") int id, @RequestParam("group") int groupId, Model model){
        List<Student> stud = studentRepository.findStudentByGroup(groupId);
        model.addAttribute("students", stud);
        model.addAttribute("teacher", teacherService.findById(id));
        model.addAttribute("gr", groupService.findById(groupId));

        return "TeacherGroupRating";
    }

    @PostMapping("/teacher/groups1/{id}/{id_student}/{id_group}")
    public String sendMarks(@PathVariable(name="id") int id,@PathVariable(name="id_student") int id_student,@PathVariable(name="id_group") int id_group, @RequestParam String rating, Model model){
        ratingStudents.checkRatingStudent(rating, id_student, studentService, groupService);
        model.addAttribute("teacher", teacherService.findById(id));
        List<Group> lst = groupRepository.findGroupsByTeacher(id);
        model.addAttribute("groups", lst);
        model.addAttribute("gr", groupService.findById(id_group));
        List<Student> stud = studentRepository.findStudentByGroup(id_group);
        model.addAttribute("students", stud);
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
    public String edit(@PathVariable("id") int id,Model model){
        Teacher teacher = teacherService.findById(id);
        model.addAttribute("teacher", teacher);
        return "EditPersonalTeacher";
    }
    @PostMapping("/teacher/personal/edit/{id}")
    public String editPersonInformation(@PathVariable("id") int id,@RequestParam("surname") String surname, @RequestParam("name") String name,
                                        @RequestParam("mail") String mail, RedirectAttributes attributes, Model model){
        Teacher teacher = teacherService.findById(id);
        User user = userService.findById(teacher.getId_user().getId_user());
        List<User> userList = userService.findAllUser();
        if(user.getMail().equals(mail)){
            //user.setMail(mail);
            user.setSurname(surname);
            user.setName(name);
            userService.save(user);
            teacher.setId_user(user);
            teacherService.save(teacher);

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
                teacher.setId_user(user);
                teacherService.save(teacher);
            }
            else{
                String error="We have user with this mail.Enter another mail please!";
                attributes.addFlashAttribute("error", error);

                return "redirect:/students/personal/edit/{id}";
            }
        }

        /*List<Group> listLast = findLastGroup.findLastGroupsStudent(studentService, groupService, id);
        for (Group course : listLast) {
            course.setProgress(ThreadLocalRandom.current().nextInt(0, 101));
        }
        *///model.addAttribute("courses", courses);
        //model.addAttribute("last", listLast);

        model.addAttribute("teacher",teacher);
        attributes.addFlashAttribute("teacher", teacher);
        return "TeacherFirst";
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
            model.addAttribute("notFound", notFound);
        }
        model.addAttribute("free",freeGroups);
        model.addAttribute("teacher", teacher);
        return "TeachCourse";
    }
    @PostMapping("/teacher/groups/teach/{id_teacher}/{id_group}")
    public String startedTeach(@PathVariable("id_teacher") int id,@PathVariable("id_group") int id_group, RedirectAttributes attributes, Model model){
        Teacher teacher = teacherService.findById(id);
        Group group1 = groupService.findById(id_group);
        group1.setTeacher(teacher);
        groupService.save(group1);
        List<Group> groupList = groupRepository.findGroupsByTeacher(id);

        if(groupList.size()==0){
            String notFound = "У всех групп есть преподаватели, мы сообщим вам если появяться обновления";
            model.addAttribute("notFound", notFound);
        }
        model.addAttribute("list",groupList);
        model.addAttribute("teacher", teacher);


        return "findGroupsTeacher";
    }
    @GetMapping("/teacher/personal/delete/{id}")
    public String delete(@PathVariable("id") int id, Model model, RedirectAttributes attributes){
        Teacher teacher = teacherService.findById(id);
        teacher.setCheck("1");
        teacherService.save(teacher);
        model.addAttribute("teacher",teacher);
        attributes.addFlashAttribute("teacher", teacher);
        return "TeacherFirst";
    }

}
