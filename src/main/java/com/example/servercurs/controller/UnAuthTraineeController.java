package com.example.servercurs.controller;

import com.example.servercurs.service.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trainee")
public class UnAuthTraineeController {

    private final TraineeService traineeService;

    @PostMapping
    public String addNewTrainee(@RequestParam String mail, RedirectAttributes attributes) {

        traineeService.replyUnAuthPerson(mail);
        return "redirect:/";
    }
}
