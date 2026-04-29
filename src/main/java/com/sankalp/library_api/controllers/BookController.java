package com.sankalp.library_api.controllers;

import com.sankalp.library_api.dtos.BookCreateRequest;
import com.sankalp.library_api.dtos.MemberCreateRequest;
import com.sankalp.library_api.models.Book;
import com.sankalp.library_api.services.BookService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    @GetMapping
    public ResponseEntity<Page<Book>> fetchAllBooks(@RequestParam Pageable pageable) {
        Page<Book> bookPage = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(bookPage);
    }

    @PostMapping
    public Book addBook(@Valid @RequestBody BookCreateRequest requestDTO) {
        return bookService.saveBook(requestDTO);
    }

    @GetMapping("/{id}")
    public Book fetchBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookCreateRequest dto) {

            Book updatedBook = bookService.updateBook(id, dto);
            return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> fetchBookByAuthor(@RequestParam String author) {
        List<Book> books = bookService.getBookByAuthor(author);
        return ResponseEntity.ok(books);
    }

    @PatchMapping("/borrow/{bookId}")
    public ResponseEntity<Book> borrowBook(@PathVariable Long bookId, @RequestParam Long memberId) {
        Book book = bookService.borrowBook(bookId, memberId);
        return ResponseEntity.ok(book);
    }
}
