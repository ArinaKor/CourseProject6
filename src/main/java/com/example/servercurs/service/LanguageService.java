package com.example.servercurs.service;

import com.example.servercurs.entities.Language;
import com.example.servercurs.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {
    private final LanguageRepository languageRepository;
    public List<Language> findAllLanguages(){
        return languageRepository.findAll();
    }
    public Language findById(int id){
        return languageRepository.findById(id).orElse(null);
    }
    public Language save(Language language){
        return languageRepository.save(language);
    }
    public void delete(int id){
        languageRepository.deleteById(id);
    }
    public Language findLanguageByName_language(String name){
       return languageRepository.findLanguageByName_language(name);
    }



}
