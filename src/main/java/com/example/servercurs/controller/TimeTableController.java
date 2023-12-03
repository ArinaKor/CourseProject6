package com.example.servercurs.controller;

import com.example.servercurs.entities.Group;
import com.example.servercurs.entities.Teacher;
import com.example.servercurs.entities.User;
import com.example.servercurs.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TimeTableController {
    private final GroupService groupService;

    @GetMapping("/admin/timetable")
    public String findTime(Model model){
        List<Group> list = groupService.findAllGroups();
        Teacher teacher = new Teacher();
        User user = new User();
        user.setSurname("is empty");
        teacher.setId_user(user);
        teacher.setId_teacher(0);
        for (Group gr:list) {
            if(gr.getTeacher() == null){
                gr.setTeacher(teacher);
            }
        }

        model.addAttribute("list", list);
        return "AdminTimeTable";
    }

}
