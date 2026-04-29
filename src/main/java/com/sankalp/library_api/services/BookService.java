package com.sankalp.library_api.services;

import com.sankalp.library_api.dtos.BookCreateRequest;
import com.sankalp.library_api.exceptions.BookAlreadyBorrowedException;
import com.sankalp.library_api.exceptions.BookNotFoundException;
import com.sankalp.library_api.models.Book;

import com.sankalp.library_api.models.Member;
import org.springframework.stereotype.Service;
import com.sankalp.library_api.repositories.BookRepository;
import com.sankalp.library_api.services.MemberService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final MemberService memberService;

    public BookService(BookRepository bookRepository, MemberService memberService) {
        this.bookRepository = bookRepository;
        this.memberService = memberService;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
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

    public Book borrowBook(Long bookId, Long memberId) {
        Member member = memberService.getMemberById(memberId);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        boolean isAvailable = book.getAvailable();
        if(!isAvailable) {
            throw new BookAlreadyBorrowedException(bookId);
        }

        book.setAvailable(false);
        return bookRepository.save(book);
    }
}
