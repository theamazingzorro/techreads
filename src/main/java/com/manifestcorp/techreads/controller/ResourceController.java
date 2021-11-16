package com.manifestcorp.techreads.controller;

import com.manifestcorp.techreads.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class ResourceController {

    @GetMapping
    public Book find() {
        return new Book();
    }
}
