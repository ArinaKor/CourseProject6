package com.example.servercurs.controller;

import com.example.servercurs.entities.Group;
import com.example.servercurs.entities.TimeTable;
import com.example.servercurs.enums.DayOfWeek;
import com.example.servercurs.repository.GroupRepository;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class TimeTableController {
    @Autowired
    private TimetableService timetableService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("/admin/timetable")
    public String findTime(Model model){
        List<Group> list = groupService.findAllGroups();

        model.addAttribute("list", list);
        return "AdminTimeTable";
    }

}
