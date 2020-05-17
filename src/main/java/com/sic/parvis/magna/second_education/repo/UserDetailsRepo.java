package com.sic.parvis.magna.second_education.repo;

import com.sic.parvis.magna.second_education.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepo extends JpaRepository<User, String> {
    @EntityGraph(attributePaths = {"subscriptions", "subscribers"})
    Optional<User> findById(String id);
}
