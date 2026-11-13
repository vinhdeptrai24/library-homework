package com.example.library.controller;

import com.example.library.dto.BookResponse;
import com.example.library.dto.CreateBookRequest;
import com.example.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody CreateBookRequest request) {
        BookResponse created = bookService.create(request);
        return ResponseEntity.created(URI.create("/api/books/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public BookResponse getById(@PathVariable Long id) {
        return bookService.getById(id);
    }
}