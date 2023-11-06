package com.example.servercurs.Certificate;

import com.example.servercurs.entities.Group;
import com.example.servercurs.entities.Student;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.StudentService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FindLastGroup {

    public List<Group> findLastGroupsStudent(StudentService studentService, GroupService groupService, int id_stud){
        Student student = studentService.findById(id_stud);
        if(student.getCourses()==null){
            student.setCourses("0,");
        }
        List<Group> list = groupService.findAllGroups();
        String grs = student.getCourses();
        String[] last = grs.split(",");
        Set<String> set = new HashSet<>();
        for (String i : last) {
            set.add(i);
        }

        String[] result = new String[set.size()];
        int k = 0;
        for (String i: set) {
            result[k++] = i;
        }
        List<Group> groupList = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < list.size(); j++) {
                if(Integer.parseInt(result[i])==list.get(j).getId_group()){
                    groupList.add(list.get(j));
                }

            }

        }
       return groupList;
    }
}
