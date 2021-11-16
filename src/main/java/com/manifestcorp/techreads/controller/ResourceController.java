package com.manifestcorp.techreads.controller;

import com.manifestcorp.techreads.model.Book;
import com.manifestcorp.techreads.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class ResourceController {

    @Autowired
    BookRepository repository;

    @GetMapping
    public Collection<Book> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> find(@PathVariable("id")long id) {
        Optional<Book> book = repository.findById(id);

        if(book.isPresent()) {
            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Book> add(@RequestBody Book response) {
        Book newBook = new Book(response);
        repository.saveAndFlush(newBook);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable("id")long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable("id")Long id, @RequestBody Book response) {
        Optional<Book> bookData = repository.findById(id);

        if(bookData.isPresent()) {
            Book book = bookData.get();
            book.copy(response);
            repository.saveAndFlush(book);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
