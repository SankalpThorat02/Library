package com.sankalp.library_api.services;

import com.sankalp.library_api.dao.BookDao;
import com.sankalp.library_api.dtos.BookCreateRequest;
import com.sankalp.library_api.exceptions.BookAlreadyBorrowedException;
import com.sankalp.library_api.exceptions.BookNotBorrowedException;
import com.sankalp.library_api.exceptions.BookNotFoundException;
import com.sankalp.library_api.models.Book;

import com.sankalp.library_api.models.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Service;
import com.sankalp.library_api.repositories.BookRepository;
import com.sankalp.library_api.services.MemberService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class BookService {

    @PersistenceContext
    private EntityManager entityManager;

    private final BookRepository bookRepository;
    private final MemberService memberService;
    private final BookDao bookDao;

    public BookService(BookRepository bookRepository, MemberService memberService, BookDao bookDao) {
        this.bookRepository = bookRepository;
        this.memberService = memberService;
        this.bookDao = bookDao;
    }

    public List<Book> getAllBooks() {
        return entityManager.createNamedQuery("Book.findAllAvailableBooks", Book.class).getResultList();
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
        log.info("Member ID: {} is attempting to borrow Book ID: {}", memberId, bookId);

        Member member = memberService.getMemberById(memberId);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    log.error("Failed to borrow: Book ID {} not found in database", bookId);
                    return new BookNotFoundException(bookId);
                });

        boolean isAvailable = book.getAvailable();
        if(!isAvailable) {
            log.warn("Member ID {} tried to borrow Book ID {}, but it is already checked out", memberId, bookId);
            throw new BookAlreadyBorrowedException(bookId);
        }

        book.setAvailable(false);
        log.info("Successfully checked out Book ID {} to Member ID {}", bookId, memberId);
        return bookRepository.save(book);
    }

    public Book returnBook(Long bookId, Long memberId) {
        Member member = memberService.getMemberById(memberId);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        boolean isAvailable = book.getAvailable();
        if(isAvailable) {
            throw new BookNotBorrowedException(bookId);
        }

        book.setAvailable(true);
        return bookRepository.save(book);
    }

    public BigDecimal calculateReturnFee(int daysLate) {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("Book.calculateLateFee");

        query.setParameter("in_days_late", daysLate);
        query.execute();

        return (BigDecimal) query.getOutputParameterValue("out_total_fee");
    }

    public Long createNewBook(BookCreateRequest dto) {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("Book.insertNewBook");

        query.setParameter("in_author", dto.getAuthor());
        query.setParameter("in_title", dto.getTitle());
        query.setParameter("in_published_year", dto.getPublishedYear());

        query.execute();

        return (Long) query.getOutputParameterValue("out_new_id");
    }

    public String checkoutBook(Long bookId) {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("Book.checkout");

        query.setParameter("in_book_id", bookId);
        query.execute();

        return (String) query.getOutputParameterValue("out_message");
    }

    public  String deleteExistingBook(Long bookId) {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("Book.deleteBook");

        query.setParameter("in_book_id", bookId);
        query.execute();

        return (String) query.getOutputParameterValue("out_message");
    }

    @Transactional
    public String checkoutAndlog(Long bookId, String librarianName) {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("Book.checkoutWithLog");

        query.setParameter("in_book_id", bookId);
        query.setParameter("in_librarian_name", librarianName);
        query.execute();

        return (String) query.getOutputParameterValue("out_message");
    }
}
