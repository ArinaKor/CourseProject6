package com.example.servercurs.service;

import com.example.servercurs.entities.Group;
import com.example.servercurs.entities.TimeTable;
import com.example.servercurs.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @InjectMocks
    private GroupService groupService;
    
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private TimetableService timetableService;

    @Mock
    private  SkillsService skillsService;
    @Mock
    private  LanguageService languageService;
    @Mock
    private  TeacherService teacherService;
    @Mock
    private  CourseService courseService;

    private TimeTable timeTable;
    private TimeTable timeTable2;
    private Group group;
    private List<TimeTable> timeTableList = new ArrayList<>();

    private List<Group> groups = new ArrayList<>();

    @BeforeEach
    void setUp() {
        timeTable = TimeTable.builder()
                .dayOfWeek("MONDAY")
                .time("12:00-14:00")
                .id_timetable(1)
                .build();

        timeTable2 = TimeTable.builder()
                .dayOfWeek("MONDAY")
                .time("16:00-18:00")
                .id_timetable(2)
                .build();

        group = Group.builder()
                .id_group(0)
                .count_student_all(12)
                .recorded_count(1)
                .progress(15)
                .timetable(timeTable2)
                .build();

        groups = List.of(group);
        timeTableList = List.of(timeTable);
    }

    @Test
    void addGroup() {
        //ARRANGE
        when(timetableService.findAllTimeTables()).thenReturn(timeTableList);
        when(groupRepository.findAll()).thenReturn(groups);

        //ACT
        List<TimeTable> tm2 = groupService.addGroup();

        //ASSERT
        assertEquals(1, tm2.size());
        assertEquals(timeTable, tm2.get(0));
    }

    @Test
    void addGroup1() {
    }

    @Test
    void update() {
    }

    @Test
    void findAllGroupsByTeacher() {
    }
}