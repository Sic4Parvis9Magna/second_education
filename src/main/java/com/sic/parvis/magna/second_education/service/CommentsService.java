package com.sic.parvis.magna.second_education.service;

import com.sic.parvis.magna.second_education.dto.CommentDto;
import com.sic.parvis.magna.second_education.dto.EventType;
import com.sic.parvis.magna.second_education.dto.ObjectType;
import com.sic.parvis.magna.second_education.model.Comment;
import com.sic.parvis.magna.second_education.model.User;
import com.sic.parvis.magna.second_education.model.Views;
import com.sic.parvis.magna.second_education.repo.CommentRepo;
import com.sic.parvis.magna.second_education.repo.UniversityRepo;
import com.sic.parvis.magna.second_education.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentsService {
    private final CommentRepo commentRepo;
    private final UniversityRepo universityRepo;
    private final BiConsumer<EventType, CommentDto> wsSenderWithData;

    @Autowired
    public CommentsService(UniversityRepo universityRepo, CommentRepo commentRepo, WsSender wsSender) {
        this.universityRepo = universityRepo;
        this.commentRepo = commentRepo;
        this.wsSenderWithData = wsSender.getSender(ObjectType.COMMENT, Views.WithData.class);
    }

    public Comment create(CommentDto commentDto, User user) {
        var uni = universityRepo.getOne(commentDto.getUniversityId());
        var comment = new Comment();
        comment.setAuthor(user);
        comment.setText(commentDto.getText());
        comment.setUniversity(uni);

        var savedComment = commentRepo.save(comment);
        wsSenderWithData.accept(EventType.CREATE, new CommentDto(savedComment));

        return savedComment;
    }
}
