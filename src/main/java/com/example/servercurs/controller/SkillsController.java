package com.example.servercurs.controller;

import com.example.servercurs.entities.Language;
import com.example.servercurs.entities.Skills;
import com.example.servercurs.service.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SkillsController {
    @Autowired
    private SkillsService skillsService;
    @GetMapping("/admin/skills")
    public String FindLand(Model model){
        List<Skills> list = skillsService.findAllSkillss();
        model.addAttribute("list", list);
        return "Skills";
    }

    @GetMapping("/admin/skills/add")
    public String AddCourse(Model model){

        return "AddSkills";
    }
    @PostMapping("/admin/skills/add")
    public String AddNewLang(@RequestParam String skills, Model model ){
        Skills skill = new Skills();
        skill.setName_skills(skills);
        skillsService.save(skill);
        return "redirect:/admin/skills";
    }

    @PostMapping("/admin/skills/{id_skills}/delete")
    public String delete(@PathVariable(value = "id_skills") int id_skills, Model model){
        skillsService.delete(id_skills);
        return "redirect:/admin/skills";
    }
    @GetMapping("/admin/skills/{id_skills}/edit")
    public String edit(@PathVariable(value = "id_skills") int id_skills, Model model){
        Skills skills = skillsService.findById(id_skills);
        model.addAttribute("el", skills);
        return "EditSkills";
    }
    @PostMapping("/admin/skills/{id_skills}/edit")
    public String update(@PathVariable(value = "id_skills") int id_skills, @RequestParam String skills, Model model){
        Skills skill = skillsService.findById(id_skills);
        skill.setName_skills(skills);
        skillsService.save(skill);
        return "redirect:/admin/skills";
    }
}
