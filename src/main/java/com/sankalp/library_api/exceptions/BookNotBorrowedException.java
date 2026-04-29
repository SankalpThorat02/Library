package com.sankalp.library_api.exceptions;

public class BookNotBorrowedException extends RuntimeException {
    public BookNotBorrowedException(Long id) {
        super("Book with ID: " + id + " is not borrowed");
    }
}
