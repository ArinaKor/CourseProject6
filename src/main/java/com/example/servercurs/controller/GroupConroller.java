package com.example.servercurs.controller;

import com.example.servercurs.entities.Group;
import com.example.servercurs.entities.Language;
import com.example.servercurs.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;

@Controller
public class GroupConroller {
    @Autowired
    private GroupService groupService;
    @GetMapping("/admin/groups")
    private String findGroups(Model model){
        List<Group> list = groupService.findAllGroups();
        model.addAttribute("list",list);
        return "AdminGroups";
    }
    @PostMapping("/admin/groups/{id_group}/delete")
    public String deleteGroup(@PathVariable(name = "id_group") int id_group, Model model){
        groupService.delete(id_group);
        return "redirect:/admin/groups";
    }
    @GetMapping("/admin/groups/add")
    public String AddCourse(Model model){

        return "addGroup";
    }
    @PostMapping("/admin/groups/add")
    public String AddNewLang(@RequestParam int countAll, @RequestParam String group_time, @RequestParam int recorded, @RequestParam Date date, @RequestParam int teacher, @RequestParam int course , Model model ){
       Group group = new Group();
       group.setCount_student_all(countAll);
       group.setGroup_time(group_time);
       group.setRecorded_count(recorded);
       group.setDate_start(date);
       //teacher and course!
        return "redirect:/admin/groups";
    }

}
