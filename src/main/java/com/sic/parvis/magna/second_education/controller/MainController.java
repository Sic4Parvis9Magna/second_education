package com.sic.parvis.magna.second_education.controller;

import com.sic.parvis.magna.second_education.model.User;
import com.sic.parvis.magna.second_education.repo.UniversityRepo;
import lombok.RequiredArgsConstructor;
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

    private final UniversityRepo universityRepo;

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();

        data.put("profile", user);
        data.put("universities", universityRepo.findAll());

        model.addAttribute("frontendData", data);

        return "index";
    }
}
