package com.sic.parvis.magna.second_education.model;

import lombok.Data;

import java.time.Duration;
import java.util.List;

@Data
public class Department {
    private List<Subject> subjects;
    private Duration duration;
    private Double cost;
    private List<Exam> exams;
}
