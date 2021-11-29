package com.manifestcorp.techreads.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.manifestcorp.techreads.model.Book;
import com.manifestcorp.techreads.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(BookController.class)
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    private List<Book> books;

    @BeforeEach
    void setup() {
        this.books = new ArrayList<Book>();
        for(int i = 0; i < 10; i++) {
            books.add(i, new Book("test " + i, "author", "url", 2.5));
        }

        when(this.bookRepository.findAll()).thenReturn(this.books);
    }

    @Test
    void testInitBookList() throws Exception {
        mockMvc.perform(get("/books")).andExpect(status().isOk())
                .andExpect(model().attribute("books", books))
                .andExpect(view().name("books"));
    }

    @Test
    void testAddBookPage() throws Exception {
        mockMvc.perform(get("/books/add")).andExpect(status().isOk())
                .andExpect(model().attribute("bookForm.title", new Book().getTitle()))
                .andExpect(view().name("add"));
    }

    @Test
    void testAddBookFormSubmit() throws Exception {
        Book testBook = new Book("test_title", "test_author", "test_url", 3);
        mockMvc.perform(post("/books/submit").param("title", testBook.getTitle())
                        .param("author", testBook.getAuthor()).param("coverURL", testBook.getTitle())
                        .param("rating", ""+testBook.getRating()))
                .andExpect(status().is3xxRedirection());

    }
}
