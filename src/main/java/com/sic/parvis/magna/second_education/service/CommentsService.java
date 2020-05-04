package com.sic.parvis.magna.second_education.service;

import com.sic.parvis.magna.second_education.dto.CommentDto;
import com.sic.parvis.magna.second_education.model.Comment;
import com.sic.parvis.magna.second_education.model.User;
import com.sic.parvis.magna.second_education.repo.CommentRepo;
import com.sic.parvis.magna.second_education.repo.UniversityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentRepo commentRepo;
    private final UniversityRepo universityRepo;

    public Comment create(CommentDto commentDto, User user) {
        var uni = universityRepo.getOne(commentDto.getUniversityId());
        var comment = new Comment();
        comment.setAuthor(user);
        comment.setText(commentDto.getText());
        comment.setUniversity(uni);

        return commentRepo.save(comment);
    }
}
