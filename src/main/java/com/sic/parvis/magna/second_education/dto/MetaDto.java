package com.sic.parvis.magna.second_education.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "from")
public class MetaDto {
    private String title;
    private String description;
    private String cover;
}
