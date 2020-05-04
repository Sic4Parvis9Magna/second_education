package com.sic.parvis.magna.second_education.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = {"id"})
public class University {
    //    private List<Department> departments;
    //    private Country country;

    public University(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonView(Views.WithData.class)
    private  User author;

    @OneToMany(mappedBy = "university", orphanRemoval = true)
    @JsonView(Views.WithData.class)
    private List<Comment> comments = new ArrayList<>();

    @JsonView(Views.WithData.class)
    private String link;
    @JsonView(Views.WithData.class)
    private String linkTitle;
    @JsonView(Views.WithData.class)
    private String linkDescription;
    @JsonView(Views.WithData.class)
    private String linkCover;
}
