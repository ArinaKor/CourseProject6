package com.example.servercurs.service;

import com.example.servercurs.entities.Language;
import com.example.servercurs.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {
    private final LanguageRepository languageRepository;
    private static final String BASE_PACKAGE = "com.example.servercurs";
    public LanguageService() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(BASE_PACKAGE);
        languageRepository = context.getBean(LanguageRepository.class);
    }
    // ticketObserver = new TicketObserver();
    @Autowired
    public LanguageService(LanguageRepository languageRepository){
        this.languageRepository = languageRepository;
    }
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

}
