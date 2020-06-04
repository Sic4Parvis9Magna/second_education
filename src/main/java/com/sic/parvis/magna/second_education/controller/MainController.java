package com.sic.parvis.magna.second_education.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sic.parvis.magna.second_education.dto.UniversityPageDto;
import com.sic.parvis.magna.second_education.model.University;
import com.sic.parvis.magna.second_education.model.User;
import com.sic.parvis.magna.second_education.model.Views;
import com.sic.parvis.magna.second_education.repo.UserDetailsRepo;
import com.sic.parvis.magna.second_education.service.UniversityService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    private final UniversityService universityService;
    private final ObjectWriter universityWriter;
    private final ObjectWriter profileWriter;
    private final UserDetailsRepo userDetailsRepo;

    @Autowired
    public MainController(UniversityService universityService, ObjectMapper mapper, UserDetailsRepo userDetailsRepo) {
        this.universityService = universityService;
        this.userDetailsRepo = userDetailsRepo;

        var configuredMapper = mapper.setConfig(mapper.getSerializationConfig());

        this.universityWriter = configuredMapper.writerWithView(Views.WithData.class);
        this.profileWriter = configuredMapper.writerWithView(Views.FullProfile.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();

        if (user != null) {

            var userFromDB = userDetailsRepo.findById(user.getId()).get();
            model.addAttribute("profile", getFormattedUser(userFromDB));

            var dto = getInitialUniversities(user);
            data.put("currentPage", dto.getCurrentPage());
            data.put("totalPages", dto.getTotalPages());
            model.addAttribute("universities", getFormattedUniversities(dto.getUniversities()));
        } else {
            model.addAttribute("universities", "[]");
            model.addAttribute("profile", "null");
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", true);

        return "index";
    }

    @SneakyThrows
    private String getFormattedUniversities(List<University> universities) {
        return universityWriter.writeValueAsString(universities);
    }

    @SneakyThrows
    private String getFormattedUser(User user) {
        return profileWriter.writeValueAsString(user);
    }

    private UniversityPageDto getInitialUniversities(User user) {
        var sort = Sort.by(Sort.Direction.DESC, "id");
        var pageable = PageRequest.of(0, UniversityController.PAGE_SIZE, sort);

        return  universityService.findForUser(pageable, user);
    }
}
