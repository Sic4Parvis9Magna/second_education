package com.sic.parvis.magna.second_education.repo;

import com.sic.parvis.magna.second_education.model.University;
import com.sic.parvis.magna.second_education.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UniversityRepo extends JpaRepository<University, Long> {
    @EntityGraph(attributePaths = {"comments"})
    Page<University> findByAuthorIn(List<User> users, Pageable pageable);
    Optional<University> findById(Long id);
}
