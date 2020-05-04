package com.sic.parvis.magna.second_education.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.sic.parvis.magna.second_education.model.Comment;
import com.sic.parvis.magna.second_education.model.User;
import com.sic.parvis.magna.second_education.model.Views;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDto {
    @JsonView(Views.WithData.class)
    private Long id;
    @JsonView(Views.WithData.class)
    private String text;
    @JsonView(Views.WithData.class)
    private Long universityId;
    @JsonView(Views.WithData.class)
    private User author;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.universityId = comment.getUniversity().getId();
        this.author = comment.getAuthor();
    }
}
