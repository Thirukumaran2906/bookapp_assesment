package com.hexaware.bookapp.service;


import com.hexaware.bookapp.entity.Book;
import com.hexaware.bookapp.exception.ResourceNotFoundException;
import com.hexaware.bookapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepo;

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Book getBookByIsbn(String isbn) {
        return bookRepo.findById(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    public Book addBook(Book book) {
        return bookRepo.save(book);
    }

    public Book updateBook(String isbn, Book book) {
        Book existing = getBookByIsbn(isbn);
        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setPublicationYear(book.getPublicationYear());
        return bookRepo.save(existing);
    }

    public void deleteBook(String isbn) {
        bookRepo.deleteById(isbn);
    }
}
