package com.example.manager_garage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.manager_garage.entity.auth.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}