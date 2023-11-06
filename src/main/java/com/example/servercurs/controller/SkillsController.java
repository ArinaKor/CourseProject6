package com.example.servercurs.controller;

import com.example.servercurs.entities.Skills;
import com.example.servercurs.service.SkillsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/skills")
public class SkillsController {

    private final SkillsService skillsService;

    @GetMapping
    public String findSkills(Model model){
        List<Skills> list = skillsService.findAllSkillss();
        model.addAttribute("list", list);
        return "Skills";
    }

    @GetMapping("/add")
    public String addSkills(Model model){

        return "AddSkills";
    }

    @PostMapping("/add")
    public String addNewSkill(@RequestParam String skills, Model model ){
       int count = skillsService.addLang(skills);
        if(count!=0){
            String error="We have skills with this name.Enter another name please!";
            model.addAttribute("error", error);
            return "AddSkills";
        }
        return "redirect:/admin/skills";
    }

    @PostMapping("/{id_skills}/delete")
    public String delete(@PathVariable(value = "id_skills") int id_skills, Model model){
        skillsService.delete(id_skills);
        return "redirect:/admin/skills";
    }

    @GetMapping("/{id_skills}/edit")
    public String edit(@PathVariable(value = "id_skills") int id_skills, RedirectAttributes attributes, Model model){
        Skills skills = skillsService.findById(id_skills);
        model.addAttribute("el", skills);
        attributes.addFlashAttribute("el", skills);
        return "EditSkills";
    }

    @PostMapping("/{id_skills}/edit")
    public String update(@PathVariable(value = "id_skills") int id_skills, @RequestParam("skills") String skills, Model model){
        Skills skill = skillsService.findById(id_skills);
        if((skill.getName_skills().toLowerCase()).equals(skills.toLowerCase())){
            return "redirect:/admin/skills";
        }
        int count = skillsService.update(id_skills, skills);
        if(count!=0){
            String error="We have skills with this name.Enter another name please!";
            model.addAttribute("error", error);
            model.addAttribute("el", skillsService.findById(id_skills));

            return "EditSkills";
        }
        return "redirect:/admin/skills";
    }
}
