package com.sankalp.library_api.services;

import com.sankalp.library_api.dtos.BookCreateRequest;
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
}
