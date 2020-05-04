package com.sic.parvis.magna.second_education.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sic.parvis.magna.second_education.model.User;
import com.sic.parvis.magna.second_education.model.Views;
import com.sic.parvis.magna.second_education.repo.UniversityRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class MainController {

    private final UniversityRepo universityRepo;
    private final ObjectWriter writer;

    @Autowired
    public MainController(UniversityRepo universityRepo, ObjectMapper mapper) {
        this.universityRepo = universityRepo;
        this.writer = mapper.setConfig(mapper.getSerializationConfig()).writerWithView(Views.WithData.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();

        if (Objects.nonNull(user)) {
            data.put("profile", user);
            model.addAttribute("universities", getFormattedUniversities());
        } else {
            model.addAttribute("universities", "{}");
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", true);

        return "index";
    }

    @SneakyThrows
    private String getFormattedUniversities() {
        return writer.writeValueAsString(universityRepo.findAll());
    }
}
