package com.sic.parvis.magna.second_education.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Data
@EqualsAndHashCode(of = { "id" })
public class Comment {

    @Id
    @GeneratedValue
    @JsonView(Views.IdName.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String text;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
//    @JsonBackReference
    private University university;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonView(Views.WithData.class)
    private User author;
}
