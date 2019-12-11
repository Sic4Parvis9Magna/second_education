package com.sic.parvis.magna.second_education.repo;

import com.sic.parvis.magna.second_education.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo extends JpaRepository<User, String> {
}
