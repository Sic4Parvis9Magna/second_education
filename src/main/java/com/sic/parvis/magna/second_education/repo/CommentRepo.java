package com.sic.parvis.magna.second_education.repo;

import com.sic.parvis.magna.second_education.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
}
