package com.example.servercurs.controller;

import com.example.servercurs.entities.*;
import com.example.servercurs.repository.LanguageRepository;
import com.example.servercurs.repository.SkillsRepository;
import com.example.servercurs.service.CourseService;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.LanguageService;
import com.example.servercurs.service.SkillsService;
import com.example.servercurs.service.TeacherService;
import com.example.servercurs.service.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/groups")
public class GroupController {

    private final GroupService groupService;
    private final TimetableService timetableService;
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final LanguageService languageService;
    private final SkillsService skillsService;

    @GetMapping
    private String findGroups(Model model, RedirectAttributes attributes){
        List<Group> list = groupService.findAllGroups();
        model.addAttribute("list",list);
        List<String> encodedImage = new ArrayList<>();
        List<Language> list1 = languageService.findAllLanguages();
        for (Group lg:list) {
            for(Language lang: list1) {
                String image = null;
                if (lg.getCourse().getId_language().getId_language()==lang.getId_language()) {
                    image = Base64.getEncoder().encodeToString(lang.getLogo());
                    encodedImage.add(image);
                }
                else {
                    continue;
                }
            }
        }
        model.addAttribute("encodedImage", encodedImage);
        attributes.addFlashAttribute("encodedImage", encodedImage);
        return "AdminGroups";
    }

    @PostMapping("/{id_group}/delete")
    public String deleteGroup(@PathVariable(name = "id_group") int id_group, Model model){
        groupService.delete(id_group);
        return "redirect:/admin/groups";
    }

    @GetMapping("/add")
    public String addGroup(RedirectAttributes attributes, Model model){
        List<String> time = Arrays.asList("08:00-10:00","10:00-12:00","12:00-14:00","14:00-16:00","16:00-18:00","18:00-20:00");
        model.addAttribute(time);

        List<TimeTable> tm2 = groupService.addGroup();
        if(tm2.size()==0){
            String err = "Места в расписании закончены. Мы сообщим вам если они появятся)";
            attributes.addFlashAttribute("err", err);
            return "redirect:/admin/groups";
        }
        model.addAttribute("courses", courseService.findAllCourse());
        model.addAttribute("timeTable", tm2);

        List<Teacher> teacherList = teacherService.findAllTeachers();
        model.addAttribute("teachers", teacherList);
        return "AddGroup";
    }

    @PostMapping("/add")
    public String addGroup1(@RequestParam(name="count_all") int count_all, @RequestParam(name="dateStart") Date dateStart, @RequestParam(name = "groupTime") String groupTime, @RequestParam("timetable") String timeTable,@RequestParam("course") String course,@RequestParam("teacher") String teach, Model model){
        groupService.addGroup1(count_all, dateStart, groupTime, timeTable, course, teach);
        return "redirect:/admin/groups";
    }

    @GetMapping("/edit/{id}")
    public String edit(RedirectAttributes attributes,@PathVariable(name="id") int id_group, Model model){

        List<TimeTable> tm2 = groupService.addGroup();
        model.addAttribute("timeTable", tm2);

        List<Teacher> teacherList = teacherService.findAllTeachers();
        model.addAttribute("teachers", teacherList);
        model.addAttribute("group", groupService.findById(id_group));
        model.addAttribute("listSkills", skillsService.findAllSkillss());
        model.addAttribute("listLang", languageService.findAllLanguages());
        return "EditGroup";
    }
    @PostMapping("/edit/{id}")
    public String editGroup(@PathVariable("id") int id, @RequestParam(name="count_all") int count_all, @RequestParam(name="dateStart") Date dateStart, @RequestParam(name = "groupTime") String groupTime, @RequestParam("timetable") String timeTable,@RequestParam("course") String course,@RequestParam("teacher") String teach, Model model){
        groupService.update(id, count_all, dateStart, groupTime, timeTable, course, teach);
        return "redirect:/admin/groups";
    }

}
