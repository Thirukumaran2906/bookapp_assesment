package com.hexaware.bookapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Book {
    @Id
    private String isbn;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    private int publicationYear;

}
