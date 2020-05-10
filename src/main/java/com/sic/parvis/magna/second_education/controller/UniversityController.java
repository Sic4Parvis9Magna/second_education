package com.sic.parvis.magna.second_education.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.sic.parvis.magna.second_education.dto.UniversityPageDto;
import com.sic.parvis.magna.second_education.model.University;
import com.sic.parvis.magna.second_education.model.User;
import com.sic.parvis.magna.second_education.model.Views;
import com.sic.parvis.magna.second_education.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/university")
@RequiredArgsConstructor
public class UniversityController {

    public static final int PAGE_SIZE = 3;
    private final UniversityService universityService;

    @GetMapping
    @JsonView(Views.WithData.class)
    public UniversityPageDto listUniversities(@PageableDefault(size = PAGE_SIZE, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return universityService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public University getUniversity(@PathVariable("id") University university) {
        return university;
    }

    @PostMapping
    public University createUniversity(@RequestBody University university,
                                       @AuthenticationPrincipal User user) {
        return universityService.create(university, user);
    }

    @PutMapping
    @JsonView(Views.WithData.class)
    public University updateUniversityName(@RequestParam("id") University entityFromDb, @RequestBody University updatedEntity) {
        return universityService.updateName(entityFromDb, updatedEntity);
    }

    @DeleteMapping
    public void deleteUniversity(@RequestParam("id") University university) {
        universityService.delete(university);
    }
}
