package com.sankalp.library_api.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@NamedNativeQuery(
        name = "Book.findAllAvailableBooks",
        query = "SELECT * FROM SANKALP_BOOKS WHERE IS_AVAILABLE = 1",
        resultClass = Book.class
)

@NamedStoredProcedureQuery(
        name = "Book.calculateLateFee",
        procedureName = "calculate_late_fee",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_days_late", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "out_total_fee", type = BigDecimal.class)
        }
)

@NamedStoredProcedureQuery(
        name = "Book.insertNewBook",
        procedureName = "insert_new_book",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_author", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_title", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_published_year", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "out_new_id", type = Long.class)
        }
)

@NamedStoredProcedureQuery(
        name = "Book.checkout",
        procedureName = "checkout_book",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_book_id", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "out_message", type = String.class)
        }
)

@NamedStoredProcedureQuery(
        name = "Book.deleteBook",
        procedureName = "delete_book",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_book_id", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "out_message", type = String.class)
        }
)

@Entity
@Table(name = "sankalp_books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;
    private String author;
    private int publishedYear;
    private boolean isAvailable = true;

    public Book() {}
    public Book(String title, String author, int publishedYear, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.isAvailable = isAvailable;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author ) { this.author = author; }

    public int getPublishedYear() { return publishedYear; }
    public void setPublishedYear(int publishedYear) { this.publishedYear = publishedYear; }

    public boolean getAvailable() { return isAvailable; }
    public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }
}
