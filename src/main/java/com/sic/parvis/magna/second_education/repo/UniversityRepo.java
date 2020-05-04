package com.sic.parvis.magna.second_education.repo;

import com.sic.parvis.magna.second_education.model.University;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UniversityRepo extends JpaRepository<University, Long> {
    @EntityGraph(attributePaths = {"comments"})
    List<University> findAll();
    Optional<University> findById(Long id);
}
