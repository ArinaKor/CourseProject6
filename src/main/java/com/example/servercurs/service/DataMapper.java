package com.example.servercurs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.servercurs.entities.Student;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;



@Service
public class DataMapper {

    public Context setData(List<Student> empolyeeList) {

        Context context = new Context();

        Map<String, Object> data = new HashMap<>();

        data.put("employees", empolyeeList);

        context.setVariables(data);

        return context;
    }
}
