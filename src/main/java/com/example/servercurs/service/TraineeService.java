package com.example.servercurs.service;

import com.example.servercurs.entities.Course;
import com.example.servercurs.entities.Trainee;
import com.example.servercurs.entities.enums.TraineeType;
import com.example.servercurs.repository.TraineeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TraineeService {

    private final TraineeRepository traineeRepository;
    private final LanguageService languageService;
    private final SkillsService skillsService;

    public List<Trainee> findAll() {
        return traineeRepository.findAll();
    }

    public Trainee findById(int id) {
        return traineeRepository.findById(id).orElse(null);
    }

    public Trainee save(Trainee trainee) {
        return traineeRepository.save(trainee);
    }

    public void delete(int id) {
        traineeRepository.deleteById(id);
    }
    public List<Trainee> findWithAll() {
        return traineeRepository.findWithAll();
    }

    public void saveCourse(String location, TraineeType type, String skills, String lang) {
        Trainee trainee = new Trainee();
        trainee.setDuration(6);
        trainee.setId_language(languageService.findLanguageByName_language(lang));
        trainee.setId_skills(skillsService.findSkillsByName_skills(skills));
        trainee.setLocation(location);
        trainee.setType(type);
        traineeRepository.save(trainee);
    }

    public void update(int traineeId, String location, TraineeType type, String skills, String lang, int duration) {
        Trainee trainee = traineeRepository.findById(traineeId).get();
        trainee.setId_language(languageService.findLanguageByName_language(lang));
        trainee.setId_skills(skillsService.findSkillsByName_skills(skills));
        trainee.setLocation(location);
        trainee.setType(type);
        trainee.setDuration(duration);
        traineeRepository.save(trainee);
    }
}
