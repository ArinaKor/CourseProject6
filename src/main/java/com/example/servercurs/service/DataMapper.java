package com.example.servercurs.service;

import com.example.servercurs.entities.Student;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;


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
