package com.manifestcorp.techreads.controller;

import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.*;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(BookController.class)
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    private List<Book> books;
    private Book testBook;
    private final long TEST_ID = 1L;

    @BeforeEach
    void setup() {
        this.books = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            books.add(i, new Book("list " + i, "author", "url", 2.5));
        }

        testBook = new Book("testing book", "test author", "test url", 3);
        testBook.setId(TEST_ID);
    }

    @Test
    void testInitBookList() throws Exception {
        when(this.bookRepository.findAll()).thenReturn(this.books);

        mockMvc.perform(get("/books")).andExpect(status().isOk())
                .andExpect(model().attribute("books", books))
                .andExpect(view().name("books"));

        verify(bookRepository).findAll();
    }

    @Test
    void testAddBookPage() throws Exception {
        mockMvc.perform(get("/books/add"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("bookForm.title", new Book().getTitle()))
                .andExpect(view().name("add"));
    }

    @Test
    void testAddBookFormSubmit() throws Exception {
        when(bookRepository.saveAndFlush(testBook)).thenReturn(testBook);

        MockHttpServletRequestBuilder mockRequest = post("/books/submit")
                .param("id", ""+testBook.getId())
                .param("title", testBook.getTitle())
                .param("author", testBook.getAuthor())
                .param("coverURL", testBook.getCoverURL())
                .param("rating", "" + testBook.getRating());

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        verify(bookRepository).saveAndFlush(testBook);
    }

    @Test
    void testRemoveBook() throws Exception {
        doNothing().when(bookRepository).deleteById(TEST_ID);

        mockMvc.perform(get("/books/remove/"+TEST_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        verify(bookRepository, times(1)).deleteById(TEST_ID);
    }

    @Test
    void testDetailPage() throws Exception {
        when(bookRepository.getById(TEST_ID)).thenReturn(testBook);

        mockMvc.perform(get("/books/details/"+TEST_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", testBook))
                .andExpect(view().name("detail"));

        verify(bookRepository).getById(TEST_ID);
    }

    @Test
    void testEditPage() throws Exception {
        when(bookRepository.getById(TEST_ID)).thenReturn(testBook);

        mockMvc.perform(get("/books/edit/"+TEST_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("bookForm", testBook))
                .andExpect(view().name("edit"));

        verify(bookRepository).getById(TEST_ID);
    }

    @Test
    void testEditFormPage() throws Exception {
        when(bookRepository.saveAndFlush(testBook)).thenReturn(testBook);

        MockHttpServletRequestBuilder mockRequest = post("/books/edit")
                .param("id", ""+testBook.getId())
                .param("title", testBook.getTitle())
                .param("author", testBook.getAuthor())
                .param("coverURL", testBook.getCoverURL())
                .param("rating", "" + testBook.getRating());

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        verify(bookRepository).saveAndFlush(testBook);
    }
}
