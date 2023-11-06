package com.example.servercurs.service;

import com.example.servercurs.entities.Skills;
import com.example.servercurs.repository.SkillsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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

    public int addLang(String skills){
        Skills skill = new Skills();
        skill.setName_skills(skills);
        List<Skills> list = skillsRepository.findAll();
        int count = 0;
        for (Skills sk:list) {
            if((sk.getName_skills().toLowerCase()).equals(skill.getName_skills().toLowerCase())){
                count++;
            }
        }
        if(count==0){
            skillsRepository.save(skill);
        }
        return count;
    }

    public int update(int id_skills, String skills){
        int count = 0;
        Skills skill = skillsRepository.findById(id_skills).get();
        List<Skills> list = skillsRepository.findAll();
        for (Skills sk:list) {
            if((sk.getName_skills().toLowerCase()).equals(skills.toLowerCase())){
                count++;
            }
        }
        if(count==0){
            skill.setName_skills(skills);
            skillsRepository.save(skill);
        }
        return count;
    }

}
