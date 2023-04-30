package com.example.servercurs.controller;

import com.example.servercurs.Rating.RatingStudents;
import com.example.servercurs.entities.Group;
import com.example.servercurs.entities.Student;
import com.example.servercurs.repository.GroupRepository;
import com.example.servercurs.repository.StudentRepository;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.StudentService;
import com.example.servercurs.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Form;
import java.util.List;

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

    RatingStudents ratingStudents = new RatingStudents();

    @GetMapping("/teacher")
    public String teacher(){
        return "TeacherFirst";
    }
    @GetMapping("/teacher/{id}")
    public String findGroups(@PathVariable int id, Model model){
        List<Group> groupList = groupRepository.findGroupsByTeacher(id);

        model.addAttribute("list",groupList);

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
}
