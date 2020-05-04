package com.sic.parvis.magna.second_education.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.sic.parvis.magna.second_education.dto.CommentDto;
import com.sic.parvis.magna.second_education.model.User;
import com.sic.parvis.magna.second_education.model.Views;
import com.sic.parvis.magna.second_education.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentsService commentsService;

    @PostMapping
    @JsonView(Views.WithData.class)
    public CommentDto createComment(@RequestBody CommentDto commentDto,
                                                @AuthenticationPrincipal User user) {
        var savedComment = commentsService.create(commentDto, user);

        return new CommentDto(savedComment);
    }
}
