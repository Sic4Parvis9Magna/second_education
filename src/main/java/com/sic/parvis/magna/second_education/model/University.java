package com.sic.parvis.magna.second_education.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table
public class University {
    //    private List<Department> departments;
    //    private Country country;

    @JsonView(Views.WithData.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime added;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.IdName.class)
    private Long id;
    @JsonView(Views.IdName.class)
    private String name;

    public University(String name) {
        this.name = name;
    }

    @JsonView(Views.WithData.class)
    private String link;
    @JsonView(Views.WithData.class)
    private String linkTitle;
    @JsonView(Views.WithData.class)
    private String linkDescription;
    @JsonView(Views.WithData.class)
    private String linkCover;

}
