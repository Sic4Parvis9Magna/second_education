package com.sic.parvis.magna.second_education.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.sic.parvis.magna.second_education.model.University;
import com.sic.parvis.magna.second_education.model.Views;
import com.sic.parvis.magna.second_education.repo.UniversityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/university")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityRepo universityRepo;

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<University> listUniversities() {
        return universityRepo.findAll();
    }

    @GetMapping("/{id}")
    public University getUniversity(@PathVariable("id") University university) {
        return university;
    }

    @PostMapping
    public University createUniversity(@RequestBody University university) {
        university.setAdded(LocalDateTime.now());

        return universityRepo.save(university);
    }

    @PutMapping
    public University updateUniversity(@RequestParam("id") University entityFromDb, @RequestBody University updatedEntity) {
        BeanUtils.copyProperties(updatedEntity, entityFromDb, "id");

        return universityRepo.save(entityFromDb);
    }

    @DeleteMapping
    public void deleteUniversity(@RequestParam("id") University university) {
        universityRepo.delete(university);
    }

    @MessageMapping("/changeUniversity")
    @SendTo("/topic/activity")
    public University change(University university) {
        return universityRepo.save(university);
    }
}
