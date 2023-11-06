package com.example.servercurs.controller;

import com.example.servercurs.entities.Group;
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

        model.addAttribute("list", list);
        return "AdminTimeTable";
    }

}
