package com.st1020.shuttermall.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.st1020.shuttermall.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String userName);

    User findByNameOrEmail(String name, String email);
}
