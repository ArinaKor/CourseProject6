package com.example.servercurs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.servercurs.entities.Student;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;



@Service
public class DataMapper {

    public Context setData(Student student) {

        Context context = new Context();

        Map<String, Object> data = new HashMap<>();

        data.put("st", student);

        context.setVariables(data);

        return context;
    }
}
