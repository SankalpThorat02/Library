package com.sankalp.library_api.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.generic.LocalVariableGen;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiError> handleBookNotFound(BookNotFoundException ex) {
        log.warn("Book Not found requested: {}", ex.getMessage());

        ApiError errorResponse = new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ApiError> handleMemberNotFound(MemberNotFoundException ex) {
        log.warn("Member Not found requested: {}", ex.getMessage());

        ApiError errorResponse = new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookAlreadyBorrowedException.class)
    public ResponseEntity<ApiError> handleBookAlreadyBorrowed(BookAlreadyBorrowedException ex) {
        log.warn("Book Already borrowed: {}", ex.getMessage());

        ApiError errorResponse = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookNotBorrowedException.class)
    public ResponseEntity<ApiError> handleBookNotBorrowedException(BookNotBorrowedException ex) {
        log.warn("Book Not Borrowed: {}", ex.getMessage());

        ApiError errorResponse = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
