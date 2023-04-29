package com.example.servercurs.service;

import com.example.servercurs.entities.Group;
import com.example.servercurs.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private static final String BASE_PACKAGE = "com.example.servercurs";
    public GroupService() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(BASE_PACKAGE);
        groupRepository = context.getBean(GroupRepository.class);
    }
    // ticketObserver = new TicketObserver();
    @Autowired
    public GroupService(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }
    public List<Group> findAllGroups(){
        return groupRepository.findAll();
    }
    public Group findById(int id){
        return groupRepository.findById(id).orElse(null);
    }
    public Group save(Group group){
        return groupRepository.save(group);
    }
    public void delete(int id){
        groupRepository.deleteById(id);
    }
/*
    public List<Group> findAllByTimetable(){
        return groupRepository.findAllByTimetable();
    }
*/
}
