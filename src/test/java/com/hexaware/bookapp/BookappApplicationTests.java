package com.hexaware.bookapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexaware.bookapp.entity.Book;
import com.hexaware.bookapp.repository.BookRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookappApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		bookRepository.deleteAll();
		Book book = new Book();
		book.setIsbn("1001");
		book.setTitle("Test Book");
		book.setAuthor("Test Author");
		book.setPublicationYear(2020);
		bookRepository.save(book);
	}

	@Test
	void contextLoads() {
		// This test ensures the application context starts correctly.
	}

	@Test
	void testGetAllBooks() throws Exception {
		mockMvc.perform(get("/api/books"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].isbn").value("1001"));
	}

	@Test
	void testGetBookByIsbn_found() throws Exception {
		mockMvc.perform(get("/api/books/1001"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("Test Book"));
	}

	@Test
	void testGetBookByIsbn_notFound() throws Exception {
		mockMvc.perform(get("/api/books/9999"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Book not found"));
	}

	@Test
	void testAddBook_success() throws Exception {
		Book newBook = new Book();
		newBook.setIsbn("2002");
		newBook.setTitle("New Book");
		newBook.setAuthor("Author Two");
		newBook.setPublicationYear(2023);

		mockMvc.perform(post("/api/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newBook)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.isbn").value("2002"));
	}

	@Test
	void testAddBook_duplicateIsbn() throws Exception {
		Book duplicateBook = new Book();
		duplicateBook.setIsbn("1001");
		duplicateBook.setTitle("Duplicate");
		duplicateBook.setAuthor("Author");
		duplicateBook.setPublicationYear(2021);

		mockMvc.perform(post("/api/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(duplicateBook)))
				.andExpect(status().isConflict())
				.andExpect(content().string("Book already exists"));
	}

	@Test
	void testUpdateBook_success() throws Exception {
		Book updatedBook = new Book();
		updatedBook.setIsbn("1001"); // same ISBN
		updatedBook.setTitle("Updated Title");
		updatedBook.setAuthor("Updated Author");
		updatedBook.setPublicationYear(2025);

		mockMvc.perform(put("/api/books/1001")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedBook)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("Updated Title"));
	}

	@Test
	void testUpdateBook_notFound() throws Exception {
		Book updatedBook = new Book();
		updatedBook.setIsbn("9999");
		updatedBook.setTitle("Missing Book");
		updatedBook.setAuthor("Nobody");
		updatedBook.setPublicationYear(2000);

		mockMvc.perform(put("/api/books/9999")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedBook)))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Book not found"));
	}

	@Test
	void testDeleteBook_success() throws Exception {
		mockMvc.perform(delete("/api/books/1001"))
				.andExpect(status().isNoContent());
	}

	@Test
	void testDeleteBook_notFound() throws Exception {
		mockMvc.perform(delete("/api/books/8888"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Book not found"));
	}
}
