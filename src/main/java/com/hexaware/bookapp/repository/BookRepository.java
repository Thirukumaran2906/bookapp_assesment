package com.hexaware.bookapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.hexaware.bookapp.entity.Book;

public interface BookRepository extends JpaRepository<Book, String> {
}

