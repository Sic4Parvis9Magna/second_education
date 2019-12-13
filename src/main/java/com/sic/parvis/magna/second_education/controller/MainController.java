package com.sic.parvis.magna.second_education.controller;

import com.sic.parvis.magna.second_education.model.User;
import com.sic.parvis.magna.second_education.repo.UniversityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

//    @Value("${spring.profiles.active}")
//    private final String profile;

    private final UniversityRepo universityRepo;

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();

        data.put("profile", user);
        data.put("universities", universityRepo.findAll());

        model.addAttribute("frontendData", data);
//        model.addAttribute("isDevMode", "dev".equals(profile));
        model.addAttribute("isDevMode", true);

        return "index";
    }
}
