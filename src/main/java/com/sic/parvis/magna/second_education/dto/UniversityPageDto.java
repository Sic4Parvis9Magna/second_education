package com.sic.parvis.magna.second_education.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.sic.parvis.magna.second_education.model.University;
import com.sic.parvis.magna.second_education.model.Views;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonView(Views.WithData.class)
public class UniversityPageDto {
    private List<University> universities;
    private int currentPage;
    private int totalPages;
}
