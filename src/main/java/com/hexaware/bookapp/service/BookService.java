package com.hexaware.bookapp.service;


import com.hexaware.bookapp.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookByIsbn(String isbn);
    Book addBook(Book book);
    Book updateBook(String isbn, Book book);
    void deleteBook(String isbn);
}
