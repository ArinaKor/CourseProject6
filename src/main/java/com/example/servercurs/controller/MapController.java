package com.example.servercurs.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Controller
public class MapController {

    private static final String API_KEY = "9d4a8d8c-7bb1-4267-891c-7f1c8bbd0509";

    /*@GetMapping("/maps")
    public String showMap(Model model) {
        String address = "Минск, улица Петруся Бровки 39";
        model.addAttribute("address", address);
        model.addAttribute("apiKey", API_KEY);
        return "YandexMaps";
    }*/

}
