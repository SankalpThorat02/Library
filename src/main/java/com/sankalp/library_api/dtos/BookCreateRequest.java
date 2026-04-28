package com.sankalp.library_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class BookCreateRequest {
    @NotBlank(message = "Title is required!")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @Min(value = 1450, message = "Year is too old to be valid!")
    @Max(value = 2026, message = "Year cannot be in future!")
    private int publishedYear;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public int getPublishedYear() { return publishedYear; }
    public void setPublishedYear(int publishedYear) { this.publishedYear = publishedYear; }
}
