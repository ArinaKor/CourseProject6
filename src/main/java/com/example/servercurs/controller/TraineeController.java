package com.example.servercurs.controller;

import com.example.servercurs.entities.Language;
import com.example.servercurs.entities.Skills;
import com.example.servercurs.entities.Trainee;
import com.example.servercurs.entities.enums.TraineeType;
import com.example.servercurs.service.CourseService;
import com.example.servercurs.service.LanguageService;
import com.example.servercurs.service.SkillsService;
import com.example.servercurs.service.TraineeReplyService;
import com.example.servercurs.service.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/trainees")
public class TraineeController {
    private final TraineeService traineeService;
    private final CourseService courseService;
    private final SkillsService skillsService;
    private final LanguageService languageService;
    private final TraineeReplyService traineeReplyService;

    @GetMapping
    public String workWithCourses(Model model){
        model.addAttribute("trainees", traineeService.findAll());
        List<String> encodedImage = courseService.encodedImageForTrainee();
        model.addAttribute("encodedImage", encodedImage);
        return "AdminTrainee";
    }

    @PostMapping("/{traineeId}/delete")
    public String deleteTrainee(@PathVariable(value = "traineeId") int traineeId){
        traineeService.delete(traineeId);
        return "redirect:/admin/trainees";
    }

    @GetMapping("/add")
    public String addNewTraineePage(Model model){
        List<TraineeType> listType = List.of(TraineeType.ONLINE, TraineeType.OFFLINE);
        model.addAttribute("listType", listType);
        List<Skills> listSkills = skillsService.findAllSkillss();
        List<Language> listLang = languageService.findAllLanguages();
        model.addAttribute("listSkills", listSkills);
        model.addAttribute("listLang", listLang);
        return "AddTrainees";
    }

    @PostMapping("/add")
    public String addNewTrainee(@RequestParam String location, @RequestParam TraineeType type, @RequestParam String skills, @RequestParam String lang){
        traineeService.saveCourse(location, type, skills, lang);
        return "redirect:/admin/trainees";
    }

    @GetMapping("/{traineeId}/edit")
    public String edit(@PathVariable(value = "traineeId") int traineeId, Model model){

        Trainee trainee = traineeService.findById(traineeId);
        model.addAttribute("el", trainee);

        List<Skills> listSkills = skillsService.findAllSkillss();
        model.addAttribute("listSkills", listSkills);

        List<Language> listLang = languageService.findAllLanguages();
        model.addAttribute("listLang", listLang);

        return "EditTrainees";
    }

    @PostMapping("/{traineeId}/edit")
    public String update(@PathVariable(value = "traineeId") int traineeId,@RequestParam int duration, @RequestParam String location, @RequestParam TraineeType type, @RequestParam String skills, @RequestParam String lang){
        traineeService.update(traineeId, location, type, skills, lang, duration);
        return "redirect:/admin/trainees";
    }

    @GetMapping("/reply")
    public String workWithReply(Model model){
        model.addAttribute("traineesReply", traineeReplyService.findAll());

        return "TraineeReply";
    }


}
