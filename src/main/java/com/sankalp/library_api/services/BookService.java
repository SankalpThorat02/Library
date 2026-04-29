package com.sankalp.library_api.services;

import com.sankalp.library_api.dtos.BookCreateRequest;
import com.sankalp.library_api.exceptions.BookAlreadyBorrowedException;
import com.sankalp.library_api.exceptions.BookNotFoundException;
import com.sankalp.library_api.models.Book;
import org.springframework.stereotype.Service;
import com.sankalp.library_api.repositories.BookRepository;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(BookCreateRequest dto) {
        Book newBook = new Book();

        newBook.setTitle(dto.getTitle());
        newBook.setAuthor(dto.getAuthor());
        newBook.setPublishedYear(dto.getPublishedYear());

        return bookRepository.save(newBook);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book updateBook(Long id, BookCreateRequest dto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        existingBook.setTitle(dto.getTitle());
        existingBook.setAuthor(dto.getAuthor());
        existingBook.setPublishedYear(dto.getPublishedYear());

        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        bookRepository.delete(book);
    }

    public List<Book> getBookByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public Book borrowBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        boolean isAvailable = book.getAvailable();
        if(!isAvailable) {
            throw new BookAlreadyBorrowedException(id);
        }

        book.setAvailable(false);
        return bookRepository.save(book);
    }
}
