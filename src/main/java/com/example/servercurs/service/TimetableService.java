package com.example.servercurs.service;

import com.example.servercurs.entities.TimeTable;
import com.example.servercurs.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableService {
    private final TimetableRepository timetableRepository;
    private static final String BASE_PACKAGE = "com.example.servercurs";
    public TimetableService() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(BASE_PACKAGE);
        timetableRepository = context.getBean(TimetableRepository.class);
    }
    // ticketObserver = new TicketObserver();
    @Autowired
    public TimetableService(TimetableRepository timetableRepository){
        this.timetableRepository = timetableRepository;
    }
    public List<TimeTable> findAllTimeTables(){
        return timetableRepository.findAll();
    }
    public TimeTable findById(int id){
        return timetableRepository.findById(id).orElse(null);
    }
    public TimeTable save(TimeTable timetable){
        return timetableRepository.save(timetable);
    }
    public void delete(int id){
        timetableRepository.deleteById(id);
    }
}
