package com.sankalp.library_api.controllers;

import com.sankalp.library_api.dtos.BookCreateRequest;
import com.sankalp.library_api.models.Book;
import com.sankalp.library_api.services.BookService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> fetchInventory() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public Book addBook(@Valid @RequestBody BookCreateRequest requestDTO) {
        return bookService.saveBook(requestDTO);
    }
}
