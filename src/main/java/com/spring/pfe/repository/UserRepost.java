package com.spring.pfe.repository;

import com.spring.pfe.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepost extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
