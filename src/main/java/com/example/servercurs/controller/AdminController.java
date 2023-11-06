package com.example.servercurs.controller;

import com.example.servercurs.entities.User;
import com.example.servercurs.repository.UserRepository;
import com.example.servercurs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/admin/personal/edit")
    public String edit(Model model){
        User user = userRepository.findByRole("admin");
        model.addAttribute("user", user);
        byte[] imageBytes = user.getPhoto();
        // Кодирование изображения в base64
        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
        model.addAttribute("encodedImage", encodedImage);
        return "AdminEditPersonal";
    }

    @PostMapping("/admin/personal/edit")
    public String editAdmin(@RequestParam("surname") String surname, @RequestParam("name") String name,
                            @RequestParam("mail") String mail, @RequestParam("photo") MultipartFile photo, RedirectAttributes attributes, Model model) throws
            IOException {
        User user = userRepository.findByRole("admin");
        model.addAttribute("user", user);
        List<User> userList = userService.findAllUser();
        String encodedImage = null;
        if (photo.isEmpty()) {
            byte[] imageBytes = user.getPhoto();
            // Кодирование изображения в base64
            encodedImage = Base64.getEncoder().encodeToString(imageBytes);
            user.setPhoto(imageBytes);
            userService.save(user);
            model.addAttribute("encodedImage", encodedImage);
            attributes.addFlashAttribute("encodedImage", encodedImage);

        } else {
            user.setPhoto(photo.getBytes());
        }
        if (user.getMail().equals(mail)) {
            user.setSurname(surname);
            user.setName(name);
            userService.save(user);
        }else {
            user.setMail(mail);
            user.setSurname(surname);
            user.setName(name);
            int count = 0;
            for (User user1 : userList) {
                if (user1.getMail().equals(user.getMail())) {
                    count++;
                }
            }
            if (count == 0) {
                userService.save(user);
            } else {
                String error = "We have user with this mail.Enter another mail please!";
                attributes.addFlashAttribute("error", error);

                return "redirect:/admin/personal/edit";
            }
        }
        return "redirect:/admin";
    }
    @GetMapping("/admin/change/password")
    public String change( Model model, RedirectAttributes attributes){
        User user = userRepository.findByRole("admin");
        model.addAttribute("user", user);
        return "ChangeAdminPass";
    }

    @PostMapping("/admin/change/password")
    public String changePassword(RedirectAttributes attributes, @RequestParam("lastPass") String lastPass, @RequestParam("newPass") String newPass, Model model){
        User user = userRepository.findByRole("admin");
        if(BCrypt.checkpw(newPass, user.getPassword())){
            String err = "Вы ввели тот же пароль что и прошлый!";
            attributes.addFlashAttribute("err", err);
            model.addAttribute("err", err);
            model.addAttribute("user", user);
            attributes.addFlashAttribute("user", user);
            return "redirect:/admin/change/password";
        }
        else if (!BCrypt.checkpw(lastPass, user.getPassword())){
            String err = "Это не ваш старый пароль!";
            attributes.addFlashAttribute("err", err);
            model.addAttribute("err", err);
            model.addAttribute("user", user);
            attributes.addFlashAttribute("user", user);
            return "redirect:/admin/change/password";
        }else{
            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(newPass, salt);
            user.setPassword(hashedPassword);
            userService.save(user);
        }

        attributes.addFlashAttribute("user", user);
        return "redirect:/admin";
    }



}
