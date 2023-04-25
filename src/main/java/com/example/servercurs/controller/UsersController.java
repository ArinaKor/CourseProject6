package com.example.servercurs.controller;

import com.example.servercurs.entities.User;
import com.example.servercurs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UsersController {
    @Autowired
    private UserService userService;
    @GetMapping("/admin/users")
    public String findUsers(Model model){
        List<User> users = userService.findAllUser();
        model.addAttribute("users", users);
        return "AdminUsers";
    }
}
