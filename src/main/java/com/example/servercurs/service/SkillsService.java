package com.example.servercurs.service;

import com.example.servercurs.entities.Skills;
import com.example.servercurs.repository.SkillsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillsService {
    private final SkillsRepository skillsRepository;
    public List<Skills> findAllSkillss(){
        return skillsRepository.findAll();
    }
    public Skills findById(int id){
        return skillsRepository.findById(id).orElse(null);
    }
    public Skills save(Skills skills){
        return skillsRepository.save(skills);
    }
    public void delete(int id){
        skillsRepository.deleteById(id);
    }

    public Skills findSkillsByName_skills(String name){
        return skillsRepository.findSkillsByName_skills(name);
    }

}
