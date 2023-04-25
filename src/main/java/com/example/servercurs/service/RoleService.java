package com.example.servercurs.service;

import com.example.servercurs.entities.Role;
import com.example.servercurs.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private static final String BASE_PACKAGE = "com.example.servercurs";
    public RoleService() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(BASE_PACKAGE);
        roleRepository = context.getBean(RoleRepository.class);
    }
    // ticketObserver = new TicketObserver();
    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }
    public Role findById(int id){
        return roleRepository.findById(id).orElse(null);
    }
    public Role save(Role role){
        return roleRepository.save(role);
    }
    public void delete(int id){
        roleRepository.deleteById(id);
    }



}
