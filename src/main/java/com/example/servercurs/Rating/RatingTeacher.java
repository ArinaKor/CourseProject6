package com.example.servercurs.Rating;

import com.example.servercurs.entities.Group;
import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.Teacher;
import com.example.servercurs.service.GroupService;
import com.example.servercurs.service.StudentService;
import com.example.servercurs.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;

public class RatingTeacher {
    public void checkRatingTeacher( String rating, int id, StudentService studentService, GroupService groupService, TeacherService teacherService){
        Student student = studentService.findById(id);
        Group group = groupService.findById(student.getId_group().getId_group());
        Teacher teacher = group.getTeacher();
        /*if(teacher.getCount_rating()==null){
            teacher.setCount_rating("");
        }*/
        StringBuilder crs = new StringBuilder(String.valueOf(teacher.getCount_rating()));
        teacher.setCount_rating(String.valueOf(crs.append(rating+",")));
        String[] cnt_rating = teacher.getCount_rating().split(",");
        double cnt = 0;
        //-=-=-=     Find rating         -=-=-=
        for (int i = 0; i < cnt_rating.length; i++) {
            cnt+=Double.parseDouble(cnt_rating[i]);
        }

        teacher.setRating(cnt/(cnt_rating.length));
        teacherService.save(teacher);
    }
}
