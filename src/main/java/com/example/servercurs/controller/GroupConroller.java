package com.example.servercurs.controller;

import com.example.servercurs.entities.*;
import com.example.servercurs.repository.CourseRepository;
import com.example.servercurs.repository.LanguageRepository;
import com.example.servercurs.repository.SkillsRepository;
import com.example.servercurs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class GroupConroller {
    @Autowired
    private GroupService groupService;
    @Autowired
    private TimetableService timetableService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SkillsRepository skillsRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TeacherService teacherService;
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
   /* @GetMapping("/admin/groups/add")
    public String AddCourse(Model model){

        return "addGroup";
    }*/
    /*@PostMapping("/admin/groups/add")
    public String AddNewLang(@RequestParam int countAll, @RequestParam String group_time, @RequestParam int recorded, @RequestParam Date date, @RequestParam int teacher, @RequestParam int course , Model model ){
       Group group = new Group();
       group.setCount_student_all(countAll);
       group.setGroup_time(group_time);
       group.setRecorded_count(recorded);
       group.setDate_start(date);
       //teacher and course!
        return "redirect:/admin/groups";
    }*/
    @GetMapping("/admin/group/add")
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
        //System.out.println(tm2);
        model.addAttribute("courses", courseService.findAllCourse());
        model.addAttribute("timeTable", tm2);
        List<Teacher> teacherList = teacherService.findAllTeachers();
        model.addAttribute("teachers", teacherList);
        return "AddGroup";
    }
    @PostMapping("/admin/group/add")
    public String addGroup1(@RequestParam(name="count_all") int count_all, @RequestParam(name="dateStart") Date dateStart, @RequestParam(name = "groupTime") String groupTime, @RequestParam("timetable") String timeTable,@RequestParam("course") String course,@RequestParam("teacher") String teach, Model model){
        TimeTable timeTable1  = new TimeTable();
        System.out.println(timeTable);
        List<TimeTable> timeTableList = timetableService.findAllTimeTables();

        String[] parts = timeTable.split(":|-");
        String dayOfWeek = parts[0];
        String startTime = parts[1] + ":" + parts[2];
        String endTime = parts[3] + ":" + parts[4];
        String resultTime = startTime+"-"+endTime;
        /*System.out.println(dayOfWeek);
        System.out.println(resultTime);*/

        for(TimeTable tb: timeTableList){
            if(tb.getDayOfWeek().equals(dayOfWeek)&&tb.getTime().equals(resultTime)){
                timeTable1.setId_timetable(tb.getId_timetable());
                timeTable1.setDayOfWeek(tb.getDayOfWeek());
                timeTable1.setTime(tb.getTime());
                break;
            }
        }

        String[] courses = course.split("/");
        //System.out.println(courses.toString());
        Skills skills  = skillsRepository.findSkillsByName_skills(courses[1]);
        Language language = languageRepository.findLanguageByName_language(courses[2]);
        Course course1 = courseRepository.findCourse(skills, language, courses[0]);
        //System.out.println(course1);

        String[] tch = teach.split("-");
        Teacher teacher = teacherService.findById(Integer.parseInt(tch[0]));
        System.out.println(teacher);


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
    @GetMapping("/admin/groups/{id_group}/edit")
    public String edit(RedirectAttributes attributes,@PathVariable(name="id_group") int id_group, Model model){

        List<TimeTable> timeTable = timetableService.findAllTimeTables();
        //List<String> dayOfWeek = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
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


        model.addAttribute("group", groupService.findById(id_group));
        model.addAttribute("listSkills", skillsRepository.findAll());
        model.addAttribute("listLang", languageRepository.findAll());
        return "EditGroup";
    }

}
