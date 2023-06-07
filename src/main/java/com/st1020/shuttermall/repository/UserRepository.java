package com.st1020.shuttermall.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.st1020.shuttermall.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String userName);

    Optional<User> findByNameOrEmail(String name, String email);
}
