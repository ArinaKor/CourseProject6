package com.example.servercurs.controller;

import com.example.servercurs.Certificate.FindLastGroup;
import com.example.servercurs.entities.Group;
import com.example.servercurs.entities.Student;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class StudentPersonalController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private GroupService groupService;
    FindLastGroup findLastGroup = new FindLastGroup();
    @GetMapping("/student/{id}")
    private String checkPersonalPage(@PathVariable(name="id") int id, RedirectAttributes attributes, Model model){
        Student student = studentService.findById(id);
        model.addAttribute(student);
        attributes.addFlashAttribute("student", student);
/*FindLastGroup*/
        List<Group> listLast = findLastGroup.findLastGroupsStudent(studentService, groupService, id);
        model.addAttribute("last", listLast);
        return "StudentPersonal";
    }
    @GetMapping("/students/personal/{id}")
    public String change(@PathVariable("id") int id_stud, Model model){
        model.addAttribute("student", studentService.findById(id_stud));
        return "ChangePassword";
    }
    @PostMapping("/students/personal/{id}")
    public String changePassword(@PathVariable("id") int id){
        //допиши это)))
        return "StudentPersonal";
    }
}
