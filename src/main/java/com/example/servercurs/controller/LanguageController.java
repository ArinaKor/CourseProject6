package com.example.servercurs.controller;

import com.example.servercurs.Config.ConvertToByte;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class LanguageController {
    @Autowired
    private LanguageService languageService;
    @GetMapping("/admin/lang")
    public String FindLand(Model model, RedirectAttributes attributes){
        List<Language> list = languageService.findAllLanguages();
        model.addAttribute("list", list);
        List<String> encodedImage = new ArrayList<>();
        for (Language lg:list) {
           String image = Base64.getEncoder().encodeToString(lg.getLogo());
           encodedImage.add(image);
        }
       // byte[] imageBytes = language.getLogo();

        // Кодирование изображения в base64
        //encodedImage = Base64.getEncoder().encodeToString(imageBytes);

        model.addAttribute("encodedImage", encodedImage);
        attributes.addFlashAttribute("encodedImage", encodedImage);
        return "LangProg";
    }

    @GetMapping("/admin/lang/add")
    public String AddCourse(Model model){

        return "addLangProg";
    }
    ConvertToByte convertToByte;
    @PostMapping("/admin/lang/add")
    public String AddNewLang(@RequestParam String lang,@RequestParam(value = "photo", required = false) MultipartFile photo, RedirectAttributes attributes, Model model ) throws IOException {
        Language language = new Language();
        language.setName_language(lang);
        List<Language> list = languageService.findAllLanguages();
        String encodedImage = null;
        if (photo.isEmpty()) {
            language.setLogo(convertToByte.convertImageToByteArray("D:\\unik\\sem6\\курсовой\\photo\\2.png"));
            byte[] imageBytes = language.getLogo();

            // Кодирование изображения в base64
            encodedImage = Base64.getEncoder().encodeToString(imageBytes);
            language.setLogo(imageBytes);
            languageService.save(language);
            model.addAttribute("encodedImage", encodedImage);
            attributes.addFlashAttribute("encodedImage", encodedImage);

        } else {
           language.setLogo(photo.getBytes());
        }
        int count = 0;
        for (Language sk:list) {
            if((sk.getName_language().toLowerCase()).equals(language.getName_language().toLowerCase())){
                count++;
            }

        }
        if(count==0){
            //userService.save(user);
            languageService.save(language);
        }
        else{
            String error="We have language with this name.Enter another name please!";
            model.addAttribute("error", error);
            return "addLangProg";
        }
        //languageService.save(language);
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
        /*byte[] imageBytes = language.getLogo();

        // Кодирование изображения в base64
        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
        model.addAttribute("encodedImage", encodedImage);
        *///attributes.addFlashAttribute("encodedImage", encodedImage);

        return "EditLang";
    }
    @PostMapping("/admin/lang/{id_language}/edit")
    public String update(@PathVariable(value = "id_language") int id_language, RedirectAttributes attributes, @RequestParam(value = "lang", required = false) String lang, @RequestParam("photo") MultipartFile photo, Model model) throws IOException {
        Language language = languageService.findById(id_language);
        if((language.getName_language().toLowerCase()).equals(lang.toLowerCase())){
            List<Language> list = languageService.findAllLanguages();
            String encodedImage = null;
            if (photo.isEmpty()) {
                //user.setPhoto(convertToByte.convertImageToByteArray("D:\\unik\\sem6\\курсовой\\photo\\2.png"));
                byte[] imageBytes = language.getLogo();

                // Кодирование изображения в base64
                encodedImage = Base64.getEncoder().encodeToString(imageBytes);
                language.setLogo(imageBytes);
                languageService.save(language);
                model.addAttribute("encodedImage", encodedImage);
                attributes.addFlashAttribute("encodedImage", encodedImage);

            } else {
                language.setLogo(photo.getBytes());
                languageService.save(language);
            }
            return "redirect:/admin/lang";
        }
        List<Language> list = languageService.findAllLanguages();
        String encodedImage = null;
        if (photo.isEmpty()) {
            //user.setPhoto(convertToByte.convertImageToByteArray("D:\\unik\\sem6\\курсовой\\photo\\2.png"));
            byte[] imageBytes = language.getLogo();

            // Кодирование изображения в base64
            encodedImage = Base64.getEncoder().encodeToString(imageBytes);
            language.setLogo(imageBytes);
            languageService.save(language);
            model.addAttribute("encodedImage", encodedImage);
            attributes.addFlashAttribute("encodedImage", encodedImage);

        } else {
            language.setLogo(photo.getBytes());
        }
        int count = 0;
        for (Language sk:list) {
            if(((sk.getName_language().toLowerCase()).equals(lang.toLowerCase()))){
                count++;
            }

        }
        if(count==0){
            //userService.save(user);
            language.setName_language(lang);

            languageService.save(language);
        }
        else{
            String error="We have language with this name.Enter another name please!";
            model.addAttribute("error", error);
            model.addAttribute("el", languageService.findById(id_language));
            return "EditLang";
        }

        //languageService.save(language);
        return "redirect:/admin/lang";
    }
}
