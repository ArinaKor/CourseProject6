package com.example.servercurs.service;

import com.example.servercurs.entities.Language;
import com.example.servercurs.entities.Skills;
import com.example.servercurs.entities.TraineeReply;
import com.example.servercurs.entities.enums.EnglishLevel;
import com.example.servercurs.entities.enums.Location;
import com.example.servercurs.entities.enums.StatusReply;
import com.example.servercurs.repository.TraineeReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TraineeReplyService {

    private final TraineeReplyRepository traineeReplyRepository;


    public List<TraineeReply> findAll() {
        return traineeReplyRepository.findAll();
    }

    public TraineeReply findById(int id) {
        return traineeReplyRepository.findById(id).orElse(null);
    }

    public TraineeReply save(TraineeReply traineeReply) {
        return traineeReplyRepository.save(traineeReply);
    }

    public void delete(int id) {
        traineeReplyRepository.deleteById(id);
    }

    Optional<TraineeReply> findExactMatch(String surname,
                                          String name,
                                          Location location,
                                          String town,
                                          String dateBirth,
                                          String mail,
                                          EnglishLevel englishLevel,
                                          String education,
                                          StatusReply statusReply,
                                          Skills idSkills,
                                          Language idLanguage){
        return traineeReplyRepository.findExactMatch(surname, name, location, town, dateBirth, mail, englishLevel, education, statusReply, idSkills, idLanguage);
    }



}
