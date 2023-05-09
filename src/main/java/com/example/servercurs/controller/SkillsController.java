package com.example.servercurs.controller;

import com.example.servercurs.entities.Language;
import com.example.servercurs.entities.Skills;
import com.example.servercurs.entities.User;
import com.example.servercurs.service.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        List<Skills> list = skillsService.findAllSkillss();
        int count = 0;
        for (Skills sk:list) {
            if((sk.getName_skills().toLowerCase()).equals(skill.getName_skills().toLowerCase())){
                count++;
            }

        }
        if(count==0){
            //userService.save(user);
            skillsService.save(skill);
        }
        else{
            String error="We have skills with this name.Enter another name please!";
            model.addAttribute("error", error);
            return "AddSkills";
        }
       // skillsService.save(skill);
        return "redirect:/admin/skills";
    }

    @PostMapping("/admin/skills/{id_skills}/delete")
    public String delete(@PathVariable(value = "id_skills") int id_skills, Model model){
        skillsService.delete(id_skills);
        return "redirect:/admin/skills";
    }
    @GetMapping("/admin/skills/{id_skills}/edit")
    public String edit(@PathVariable(value = "id_skills") int id_skills, RedirectAttributes attributes, Model model){
        Skills skills = skillsService.findById(id_skills);
        model.addAttribute("el", skills);
        attributes.addFlashAttribute("el", skills);
        return "EditSkills";
    }
    @PostMapping("/admin/skills/{id_skills}/edit")
    public String update(@PathVariable(value = "id_skills") int id_skills, @RequestParam("skills") String skills, Model model){
        Skills skill = skillsService.findById(id_skills);
        if((skill.getName_skills().toLowerCase()).equals(skills.toLowerCase())){
            return "redirect:/admin/skills";
        }
        List<Skills> list = skillsService.findAllSkillss();
        int count = 0;
        for (Skills sk:list) {
            if((sk.getName_skills().toLowerCase()).equals(skills.toLowerCase())){
                count++;
            }

        }
        if(count==0){
            skill.setName_skills(skills);

            //userService.save(user);
            skillsService.save(skill);
        }
        else{
            String error="We have skills with this name.Enter another name please!";
            model.addAttribute("error", error);
            model.addAttribute("el", skillsService.findById(id_skills));

            return "EditSkills";
        }
       // skillsService.save(skill);
        return "redirect:/admin/skills";
    }
}
