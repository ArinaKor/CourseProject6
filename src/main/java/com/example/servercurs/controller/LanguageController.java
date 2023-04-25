package com.example.servercurs.controller;

import com.example.servercurs.entities.Course;
import com.example.servercurs.entities.Language;
import com.example.servercurs.entities.Skills;
import com.example.servercurs.repository.LanguageRepository;
import com.example.servercurs.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LanguageController {
    @Autowired
    private LanguageService languageService;
    @GetMapping("/admin/lang")
    public String FindLand(Model model){
        List<Language> list = languageService.findAllLanguages();
        model.addAttribute("list", list);
        return "LangProg";
    }

    @GetMapping("/admin/lang/add")
    public String AddCourse(Model model){

        return "addLangProg";
    }
    @PostMapping("/admin/lang/add")
    public String AddNewLang(@RequestParam String lang,  Model model ){
        Language language = new Language();
        language.setName_language(lang);
        languageService.save(language);
        return "redirect:/admin/lang";
    }

    @PostMapping("/admin/lang/{id_language}/delete")
    public String delete(@PathVariable(value = "id_language") int id_language, Model model){
        languageService.delete(id_language);
        return "redirect:/admin/lang";
    }
    @GetMapping("/admin/lang/{id_language}/edit")
    public String edit(@PathVariable(value = "id_language") int id_language, Model model){
        Language language = languageService.findById(id_language);
        model.addAttribute("el", language);
        return "EditLang";
    }
    @PostMapping("/admin/lang/{id_language}/edit")
    public String update(@PathVariable(value = "id_language") int id_language, @RequestParam String lang, Model model){
        Language language = languageService.findById(id_language);
        language.setName_language(lang);
        languageService.save(language);
        return "redirect:/admin/lang";
    }
}
