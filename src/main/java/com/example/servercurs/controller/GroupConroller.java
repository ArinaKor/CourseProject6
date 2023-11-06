package com.example.servercurs.controller;

import com.example.servercurs.entities.*;
import com.example.servercurs.repository.LanguageRepository;
import com.example.servercurs.repository.SkillsRepository;
import com.example.servercurs.service.CourseService;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.LanguageService;
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
public class GroupConroller {
    private final GroupService groupService;
    private final TimetableService timetableService;
    private final CourseService courseService;
    private final SkillsRepository skillsRepository;
    private final LanguageRepository languageRepository;
    private final TeacherService teacherService;
    private final LanguageService languageService;

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
        List<TimeTable> timeTable = timetableService.findAllTimeTables();
        List<String> dayOfWeek = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        List<TimeTable> tm2 = new ArrayList<>();
        List<Group> list = groupService.findAllGroups();
        int cnt = 0;
        for (TimeTable t : timeTable) {
            boolean found = false;
            for (Group g : list) {
                if (g.getTimetable().getDayOfWeek().equals(t.getDayOfWeek()) && g.getTimetable().getTime().equals(t.getTime())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                tm2.add(t);
            }
        }
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
        TimeTable timeTable1  = new TimeTable();
        System.out.println(timeTable);
        List<TimeTable> timeTableList = timetableService.findAllTimeTables();

        String[] parts = timeTable.split(":|-");
        String dayOfWeek = parts[0];
        String startTime = parts[1] + ":" + parts[2];
        String endTime = parts[3] + ":" + parts[4];
        String resultTime = startTime+"-"+endTime;


        for(TimeTable tb: timeTableList){
            if(tb.getDayOfWeek().equals(dayOfWeek)&&tb.getTime().equals(resultTime)){
                timeTable1.setId_timetable(tb.getId_timetable());
                timeTable1.setDayOfWeek(tb.getDayOfWeek());
                timeTable1.setTime(tb.getTime());
                break;
            }
        }

        String[] courses = course.split("/");
        Skills skills  = skillsRepository.findSkillsByName_skills(courses[1]);
        Language language = languageRepository.findLanguageByName_language(courses[2]);
        Course course1 = courseService.findCourse(skills, language, courses[0]);
        String[] tch = teach.split("-");
        Teacher teacher = teacherService.findById(Integer.parseInt(tch[0]));

        Group group = new Group();
        group.setCount_student_all(count_all);
        group.setGroup_time(groupTime);
        group.setDate_start(dateStart);
        group.setTimetable(timeTable1);
        group.setCourse(course1);
        group.setTeacher(teacher);
        groupService.save(group);
        return "redirect:/admin/groups";
    }
    @GetMapping("/edit/{id}")
    public String edit(RedirectAttributes attributes,@PathVariable(name="id") int id_group, Model model){

        List<TimeTable> timeTable = timetableService.findAllTimeTables();
        List<TimeTable> tm2 = new ArrayList<>();
        List<Group> list = groupService.findAllGroups();
        int cnt = 0;
        for (TimeTable t : timeTable) {
            boolean found = false;
            for (Group g : list) {
                if (g.getTimetable().getDayOfWeek().equals(t.getDayOfWeek()) && g.getTimetable().getTime().equals(t.getTime())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                tm2.add(t);
            }
        }
        model.addAttribute("timeTable", tm2);
        List<Teacher> teacherList = teacherService.findAllTeachers();
        model.addAttribute("teachers", teacherList);
        model.addAttribute("group", groupService.findById(id_group));
        model.addAttribute("listSkills", skillsRepository.findAll());
        model.addAttribute("listLang", languageRepository.findAll());
        return "EditGroup";
    }
    @PostMapping("/edit/{id}")
    public String editGroup(@PathVariable("id") int id, @RequestParam(name="count_all") int count_all, @RequestParam(name="dateStart") Date dateStart, @RequestParam(name = "groupTime") String groupTime, @RequestParam("timetable") String timeTable,@RequestParam("course") String course,@RequestParam("teacher") String teach, Model model){
        Group group = groupService.findById(id);
       group.setCount_student_all(count_all);
        TimeTable timeTable1  = new TimeTable();
        System.out.println(timeTable);
        List<TimeTable> timeTableList = timetableService.findAllTimeTables();

        String[] parts = timeTable.split(":|-");
        String dayOfWeek = parts[0];
        String startTime = parts[1] + ":" + parts[2];
        String endTime = parts[3] + ":" + parts[4];
        String resultTime = startTime+"-"+endTime;
        for(TimeTable tb: timeTableList){
            if(tb.getDayOfWeek().equals(dayOfWeek)&&tb.getTime().equals(resultTime)){
                timeTable1.setId_timetable(tb.getId_timetable());
                timeTable1.setDayOfWeek(tb.getDayOfWeek());
                timeTable1.setTime(tb.getTime());
                break;
            }
        }

        String[] courses = course.split("/");
        Skills skills  = skillsRepository.findSkillsByName_skills(courses[1]);
        Language language = languageRepository.findLanguageByName_language(courses[2]);
        Course course1 = courseService.findCourse(skills, language, courses[0]);
        String[] tch = teach.split("-");
        Teacher teacher = teacherService.findById(Integer.parseInt(tch[0]));
        System.out.println(teacher);

        group.setGroup_time(groupTime);
        group.setDate_start(dateStart);
        group.setTimetable(timeTable1);
        group.setCourse(course1);
        group.setTeacher(teacher);
        groupService.save(group);


        return "redirect:/admin/groups";
    }

}
