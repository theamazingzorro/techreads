package com.manifestcorp.techreads.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manifestcorp.techreads.model.Book;
import com.manifestcorp.techreads.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResourceController.class)
public class ResourceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookRepository bookRepository;

    private List<Book> books;
    private Book testBook;
    private ArgumentMatcher<Book> hasTestTitle;
    private final long MISSING_ID = 0L;
    private final long TEST_ID = 1L;

    @BeforeEach
    void setup() {
        this.books = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            books.add(i, new Book("list " + i, "author", "url", 2.5));
        }

        testBook = new Book("testing book", "test author", "test url", 3);

        hasTestTitle = (Book book) -> book.getTitle().equals(testBook.getTitle());
    }

    @Test
    void testFindAll() throws Exception{
        when(bookRepository.findAll()).thenReturn(books);

        mockMvc.perform(get("/api/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$[1].title", is(books.get(1).getTitle())));

        verify(bookRepository).findAll();
    }

    @Test
    void testFindSuccess() throws Exception {
        when(bookRepository.findById(TEST_ID)).thenReturn(Optional.of(testBook));

        mockMvc.perform(get("/api/books/"+TEST_ID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is(testBook.getTitle())));

        verify(bookRepository).findById(TEST_ID);
    }

    @Test
    void testFindFailure() throws Exception {
        when(bookRepository.findById(MISSING_ID)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/"+MISSING_ID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(bookRepository).findById(MISSING_ID);
    }

    @Test
    void testAdd() throws Exception {
        when(bookRepository.saveAndFlush(argThat(hasTestTitle))).thenReturn(testBook);

        MockHttpServletRequestBuilder mockRequest = post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(testBook));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is(testBook.getTitle())));

        verify(bookRepository).saveAndFlush(argThat(hasTestTitle));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(bookRepository).deleteById(TEST_ID);

        MockHttpServletRequestBuilder mockRequest = delete("/api/books/"+TEST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest).andExpect(status().isNoContent());

        verify(bookRepository, times(1)).deleteById(TEST_ID);
    }

    @Test
    void testUpdateSuccess() throws Exception {
        when(bookRepository.findById(TEST_ID)).thenReturn(Optional.of(books.get(1)));
        when(bookRepository.saveAndFlush(argThat(hasTestTitle))).thenReturn(testBook);

        MockHttpServletRequestBuilder mockRequest = put("/api/books/"+TEST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(testBook));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is(testBook.getTitle())));

        verify(bookRepository).findById(TEST_ID);
        verify(bookRepository).saveAndFlush(argThat(hasTestTitle));
    }

    @Test
    void testUpdateFailure() throws Exception {
        when(bookRepository.findById(MISSING_ID)).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder mockRequest = put("/api/books/"+MISSING_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(testBook));

        mockMvc.perform(mockRequest).andExpect(status().isNotFound());

        verify(bookRepository).findById(MISSING_ID);
    }
}
