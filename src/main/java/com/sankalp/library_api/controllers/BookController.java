package com.sankalp.library_api.controllers;

import com.sankalp.library_api.dao.BookDao;
import com.sankalp.library_api.dtos.BookCreateRequest;
import com.sankalp.library_api.dtos.LoginRequest;
import com.sankalp.library_api.models.Book;
import com.sankalp.library_api.services.BookService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final BookDao bookDao;

    public BookController(BookService bookService, BookDao bookDao) {
        this.bookService = bookService;
        this.bookDao = bookDao;
    }

    @GetMapping
    public List<Book> fetchInventory() {
        return bookService.getAllBooks();
    }

//    @GetMapping
//    public ResponseEntity<Page<Book>> fetchAllBooks(Pageable pageable) {
//        Page<Book> bookPage = bookService.getAllBooks(pageable);
//        return ResponseEntity.ok(bookPage);
//    }

//    @PostMapping
//    public ResponseEntity<Book> addBook(@Valid @RequestBody BookCreateRequest requestDTO) {
//        Book newBook = bookService.saveBook(requestDTO)
//        return ResponseEntity.ok(newBook);
//    }

    @PostMapping
    public ResponseEntity<Long> insertBook(@Valid @RequestBody BookCreateRequest requestDTO) {
        Long id = bookService.createNewBook(requestDTO);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> fetchBook(@PathVariable Long id) {
        Book existingBook = bookService.getBookById(id);
        return ResponseEntity.ok(existingBook);
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

    @PatchMapping("/return/{bookId}")
    public ResponseEntity<Book> returnBook(@PathVariable Long bookId, @RequestParam Long memberId) {
        Book book = bookService.returnBook(bookId, memberId);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/return/fee")
    public ResponseEntity<BigDecimal> getLateFeeAmount(@RequestParam int days) {
        BigDecimal fee = bookService.calculateReturnFee(days);

        return ResponseEntity.ok(fee);
    }

    @PatchMapping ("/checkout/{bookId}")
    public ResponseEntity<String> checkoutBook(@PathVariable Long bookId) {
        String res = bookService.checkoutBook(bookId);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<String> deleteExistingBook(@PathVariable Long bookId) {
        String res = bookService.deleteExistingBook(bookId);
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/checkoutAndLog/{bookId}")
    public ResponseEntity<String> checkoutAndLogBook(@PathVariable Long bookId, @RequestParam String librarianName) {
        String res = bookService.checkoutAndlog(bookId, librarianName);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest requestDto) {
        Map<String, Object> response = bookService.login(requestDto);

        if(response.get("userId") == null) {
            return ResponseEntity.status(401).body(response);
        }

        return ResponseEntity.ok(response);
    }
}
