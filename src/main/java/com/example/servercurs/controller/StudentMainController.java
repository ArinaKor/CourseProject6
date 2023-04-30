package com.example.servercurs.controller;

import com.example.servercurs.Rating.RatingTeacher;
import com.example.servercurs.entities.Group;
import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.Teacher;
import com.example.servercurs.repository.GroupRepository;
import com.example.servercurs.repository.StudentRepository;
import com.example.servercurs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import java.util.*;

@Controller
public class StudentMainController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private EmailService emailSender;
    @Autowired
    private TeacherService teacherService;
    RatingTeacher ratingTeacher = new RatingTeacher();
    static int id = AuthorizationController.stud;
    List<String> listLastGroups = new ArrayList<>();
    @GetMapping("/student")
    private String student(Model model){
        //model.addAttribute("student", studentService.findById(id));
        return "StudentFirst";
    }
    @GetMapping("/student/{id}")
    private String checkPersonalPage(@PathVariable(name="id") int id,RedirectAttributes attributes, Model model){
        Student student = studentService.findById(id);
        model.addAttribute(student);
        attributes.addFlashAttribute("student", student);
        return "StudentPersonal";
    }
    @GetMapping("/students/groups/{id_student}")
    private String findAllGroups(@PathVariable(name="id_student") int id_student,RedirectAttributes attributes, Model model){
/*
        /*List<Student> list1 = studentService.findAllStudents();
            model.addAttribute("list", list1);*/

        Student student = studentService.findById(id_student);
        model.addAttribute("student", student);
        List<Group> listCourse = groupService.findAllGroups();
        model.addAttribute("list", listCourse);

        int k=0;
        if(student.getId_group()==null){
            k=0;
        }
        else if(!(student.getId_group()==null)){
            k++;
        }
        System.out.println(k);
        model.addAttribute("k", k);
        attributes.addFlashAttribute("student", student);
        return "StudentCourses";
    }

    @PostMapping("/student/groups/{id_student}/{id_group}/enroll")
    public String enrollCourse(@PathVariable(name="id_student") int id_student, @PathVariable(name="id_group") int id_group, Model model, RedirectAttributes attributes) throws MessagingException, jakarta.mail.MessagingException {
        Student student = studentService.findById(id_student);
        Group group = groupService.findById(id_group);

        if(student.getBalance()<group.getCourse().getPrice()){

            String message="На вашем балансе недостаточно средст, пополните баланс и попробуйте ещё раз)";
            attributes.addFlashAttribute("message", message);
            return "redirect:/students/groups/{id_student}";
        }
        else{
            student.setId_group(group);
            student.setBalance(student.getBalance()-group.getCourse().getPrice());
            studentService.save(student);
            group.setRecorded_count(group.getRecorded_count()+1);
            groupService.save(group);

        }
       // emailSender.sendSimpleMessage(student.getId_user().getMail(), "subject", "body");
        model.addAttribute("student", student);
        model.addAttribute("group", group);

        attributes.addFlashAttribute("student", student);
        return "userEnrollGroup";
    }
    @GetMapping("/students/mygroup/{id}")
    public String checkMyGroup(@PathVariable(name="id") int id,RedirectAttributes attributes, Model model){
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        attributes.addFlashAttribute("student", student);
        return "StudentGroup";
    }

    @PostMapping("/students/mygroup/{id}")
    public String completeCourse(@PathVariable(name="id") int id, @RequestParam(name="rating") String rating, RedirectAttributes attributes, Model model){

        Student student = studentService.findById(id);
        Group group = groupService.findById(student.getId_group().getId_group());

        ratingTeacher.checkRatingTeacher(rating, id, studentService, groupService, teacherService);


        group.setRecorded_count(group.getRecorded_count()+1);
        groupService.save(group);
        StringBuilder crs = new StringBuilder(student.getCourses());
        student.setCourses(String.valueOf(crs.append(student.getId_group().getId_group()+",")));
        String[] courses = student.getCourses().split(",");
        for (int i = 0; i < courses.length; i++) {
            listLastGroups.add(courses[i]);
        }

        student.setId_group(null);
        studentService.save(student);
        model.addAttribute("student", student);
        attributes.addFlashAttribute("student", student);


        return "StudentFirst";
    }
    @GetMapping("/students/lastMyGroups/{id}")
    public String findLastGroups(@PathVariable(name="id") int id, Model model){
        Student student = studentService.findById(id);
        List<Group> list = groupService.findAllGroups();
        String grs = student.getCourses();
        String[] last = grs.split(",");
        /*String[] lastgr = (String[]) Arrays.stream(last).distinct().toArray();
         */
        Set<String> set = new HashSet<>();
        for (String i : last) {
            set.add(i);
        }

        String[] result = new String[set.size()];
        int k = 0;
        for (String i: set) {
            result[k++] = i;
        }
        List<Group> groupList = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < list.size(); j++) {
                if(Integer.parseInt(result[i])==list.get(j).getId_group()){
                    groupList.add(list.get(j));
                }

            }

        }

        model.addAttribute("list", groupList);

        return "LastGroup";
    }
}
