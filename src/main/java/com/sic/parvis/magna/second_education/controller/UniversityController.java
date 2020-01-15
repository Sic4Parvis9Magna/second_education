package com.sic.parvis.magna.second_education.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.sic.parvis.magna.second_education.dto.EventType;
import com.sic.parvis.magna.second_education.dto.ObjectType;
import com.sic.parvis.magna.second_education.model.University;
import com.sic.parvis.magna.second_education.model.Views;
import com.sic.parvis.magna.second_education.repo.UniversityRepo;
import com.sic.parvis.magna.second_education.util.WsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("/university")
public class UniversityController {

    private final UniversityRepo universityRepo;
    private final BiConsumer<EventType, University> wsSender;

    @Autowired
    public UniversityController(UniversityRepo universityRepo, WsSender wsSender) {
        this.universityRepo = universityRepo;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.IdName.class);
    }


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
        University createdUniversity = universityRepo.save(university);
        wsSender.accept(EventType.CREATE, createdUniversity);

        return createdUniversity;
    }

    @PutMapping
    public University updateUniversity(@RequestParam("id") University entityFromDb, @RequestBody University updatedEntity) {
        BeanUtils.copyProperties(updatedEntity, entityFromDb, "id");
        University updatedUniversity = universityRepo.save(entityFromDb);
        wsSender.accept(EventType.UPDATE, updatedEntity);

        return updatedUniversity;
    }

    @DeleteMapping
    public void deleteUniversity(@RequestParam("id") University university) {
        universityRepo.delete(university);
        wsSender.accept(EventType.REMOVE, university);
    }
}
