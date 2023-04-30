package com.example.servercurs.controller;

import com.example.servercurs.entities.Group;
import com.example.servercurs.repository.GroupRepository;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TeacherController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private GroupRepository groupRepository;
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
}
