package com.example.servercurs.Rating;

import com.example.servercurs.entities.Group;
import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.Teacher;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.StudentService;
import com.example.servercurs.service.TeacherService;

public class RatingStudents {
    public void checkRatingStudent(String rating, int id_student, StudentService studentService, GroupService groupService){
        Student student = studentService.findById(id_student);
        Group group = groupService.findById(student.getId_group().getId_group());
        //Teacher teacher = group.getTeacher();
        if(student.getCount_rating()==null){
            student.setCount_rating("");
        }
        StringBuilder crs = new StringBuilder(String.valueOf(student.getCount_rating()));
        student.setCount_rating(String.valueOf(crs.append(rating+",")));
        String[] cnt_rating = student.getCount_rating().split(",");
        double cnt = 0;
        //-=-=-=     Find rating         -=-=-=
        for (int i = 0; i < cnt_rating.length; i++) {
            cnt+=Double.parseDouble(cnt_rating[i]);
        }

        student.setRating(cnt/(cnt_rating.length));
        studentService.save(student);

    }
}
