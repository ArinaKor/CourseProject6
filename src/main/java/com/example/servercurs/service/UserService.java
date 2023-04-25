package com.example.servercurs.service;

import com.example.servercurs.entities.Role;
import com.example.servercurs.entities.User;
import com.example.servercurs.repository.RoleRepository;
import com.example.servercurs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final String BASE_PACKAGE = "com.example.servercurs";
    public UserService() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(BASE_PACKAGE);
        userRepository = context.getBean(UserRepository.class);
    }
    // ticketObserver = new TicketObserver();
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> findAllUser(){
        return userRepository.findAll();
    }
    public User findById(int id){
        return userRepository.findById(id).orElse(null);
    }
    public User save(User user){
        return userRepository.save(user);
    }
    public void delete(int id){
        userRepository.deleteById(id);
    }

}
