package com.hexaware.bookapp.repository;

import com.hexaware.bookapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}

