package com.example.servercurs.service;

import com.example.servercurs.entities.Course;
import com.example.servercurs.entities.Group;
import com.example.servercurs.entities.Language;
import com.example.servercurs.entities.Skills;
import com.example.servercurs.entities.Teacher;
import com.example.servercurs.entities.TimeTable;
import com.example.servercurs.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final TimetableService timetableService;
    private final SkillsService skillsService;
    private final LanguageService languageService;
    private final TeacherService teacherService;
    private final CourseService courseService;

    public List<Group> findAllGroups() {
        return groupRepository.findAll();
    }

    public Group findById(int id) {
        return groupRepository.findById(id).orElse(null);
    }

    public Group save(Group group) {
        return groupRepository.save(group);
    }

    public void delete(int id) {
        groupRepository.deleteById(id);
    }

    public List<Group> findByTime(String date) {
        return groupRepository.findByGroup_time(date);
    }

    public List<Group> findByDate(Date date) {
        return groupRepository.findByDateStart(date);
    }

    public List<Group> findGroupsByTeacher(int id) {
        return groupRepository.findGroupsByTeacher(id);
    }

    public List<Group> findWithAll() {
        return groupRepository.findWithAll();
    }

    public List<Group> findByCourse(Course course) {
        return groupRepository.findByCourse(course);
    }

    public List<Group> findByListCourse(List<Course> courses) {
        return groupRepository.findByListCourse(courses);
    }

    public List<TimeTable> addGroup() {
        List<TimeTable> timeTable = timetableService.findAllTimeTables();
        List<TimeTable> tm2 = new ArrayList<>();
        List<Group> list = groupRepository.findAll();
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
        return tm2;
    }

    public void addGroup1(int count_all, Date dateStart, String groupTime, String timeTable,
                          String course, String teach) {
        TimeTable timeTable1 = new TimeTable();
        List<TimeTable> timeTableList = timetableService.findAllTimeTables();

        String[] parts = timeTable.split(":|-");
        String dayOfWeek = parts[0];
        String startTime = parts[1] + ":" + parts[2];
        String endTime = parts[3] + ":" + parts[4];
        String resultTime = startTime + "-" + endTime;


        for (TimeTable tb : timeTableList) {
            if (tb.getDayOfWeek().equals(dayOfWeek) && tb.getTime().equals(resultTime)) {
                timeTable1.setId_timetable(tb.getId_timetable());
                timeTable1.setDayOfWeek(tb.getDayOfWeek());
                timeTable1.setTime(tb.getTime());
                break;
            }
        }

        String[] courses = course.split("/");
        Skills skills = skillsService.findSkillsByName_skills(courses[1]);
        Language language = languageService.findLanguageByName_language(courses[2]);
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
        groupRepository.save(group);
    }

    public void update(int id, int count_all, Date dateStart, String groupTime, String timeTable,
                       String course, String teach) {
        Group group = groupRepository.findById(id).get();
        group.setCount_student_all(count_all);
        TimeTable timeTable1 = new TimeTable();
        System.out.println(timeTable);
        List<TimeTable> timeTableList = timetableService.findAllTimeTables();

        String[] parts = timeTable.split(":|-");
        String dayOfWeek = parts[0];
        String startTime = parts[1] + ":" + parts[2];
        String endTime = parts[3] + ":" + parts[4];
        String resultTime = startTime + "-" + endTime;
        for (TimeTable tb : timeTableList) {
            if (tb.getDayOfWeek().equals(dayOfWeek) && tb.getTime().equals(resultTime)) {
                timeTable1.setId_timetable(tb.getId_timetable());
                timeTable1.setDayOfWeek(tb.getDayOfWeek());
                timeTable1.setTime(tb.getTime());
                break;
            }
        }

        String[] courses = course.split("/");
        Skills skills = skillsService.findSkillsByName_skills(courses[1]);
        Language language = languageService.findLanguageByName_language(courses[2]);
        Course course1 = courseService.findCourse(skills, language, courses[0]);
        String[] tch = teach.split("-");
        Teacher teacher = teacherService.findById(Integer.parseInt(tch[0]));


        group.setGroup_time(groupTime);
        group.setDate_start(dateStart);
        group.setTimetable(timeTable1);
        group.setCourse(course1);
        group.setTeacher(teacher);
        groupRepository.save(group);
    }


    public List<Group> findAllGroupsByTeacher(int id_teacher) {
        return groupRepository.findGroupsByTeacher(id_teacher);
    }
}
