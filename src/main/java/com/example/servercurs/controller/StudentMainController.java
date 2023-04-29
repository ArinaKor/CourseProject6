package com.example.servercurs.controller;

import com.example.servercurs.entities.Course;
import com.example.servercurs.entities.Group;
import com.example.servercurs.entities.Student;
import com.example.servercurs.repository.GroupRepository;
import com.example.servercurs.repository.StudentRepository;
import com.example.servercurs.service.CourseService;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

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
    static int id = 0;
    @GetMapping("/authorization/personal")
    private String checkPersonalPage(Model model){
/*
        List<Student> list1 = studentService.findAllStudents();
*/
        List<Student> list1 = studentRepository.findWithAll();

        model.addAttribute("list", list1);
        return "StudentPersonal";
    }
    /*@GetMapping("/authorization/timetable/{id}")
    public String findGroups(@PathVariable int id, Model model){
        List<Group> groupList = groupRepository.findGroupsByTeacher(id);

        model.addAttribute("list",groupList);

        return "findGroupsTeacher";
    }
*/
    @PostMapping("/student/groups/{id_student}/{id_group}/enroll")
    public String enrollCourse(@PathVariable(name="id_student") int id_student, @PathVariable(name="id_group") int id_group, Model model){
        Student student = studentService.findById(id_student);
        Group group = groupService.findById(id_group);
        student.setId_group(group);
        studentService.save(student);
        group.setRecorded_count(group.getRecorded_count()+1);
        groupService.save(group);


        return "userEnrollGroup";
    }
}
