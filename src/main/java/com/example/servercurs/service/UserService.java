package com.example.servercurs.service;

import com.example.servercurs.entities.Role;
import com.example.servercurs.entities.User;
import com.example.servercurs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }

    public List<User> findUsersByRole() {
        List<User> users = userRepository.findAll();
        List<User> list = new ArrayList<>();
        for (User user : users) {
            if (user.getRole().getId_role() != 1) {
                list.add(user);
            }
        }
        return list;
    }

    public List<Role> edit(int id_user) {

        List<Role> roleList = roleService.findAllRoles();
        List<Role> lastRoles = new ArrayList<>();
        for (Role rl : roleList) {
            if (rl.getRoleName().equals("admin")) {
                continue;
            } else {
                lastRoles.add(rl);
            }
        }
        return lastRoles;
    }

    public List<Role> add() {
        List<Role> roleList = roleService.findAllRoles();
        List<Role> lastRoles = new ArrayList<>();
        for (Role rl : roleList) {
            if (rl.getRoleName().equals("admin")) {
                continue;
            } else {
                lastRoles.add(rl);
            }
        }
        return lastRoles;
    }

    public User findByRoleAdmin(String role){
        return userRepository.findByRole(role);
    }


}
