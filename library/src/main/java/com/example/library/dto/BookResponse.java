package com.example.library.dto;

import com.example.library.entity.Books;

public record BookResponse (
        Long id,
        String isbn,
        String title,
        Integer totalCopies,
        Integer availableCopies

) {
    public static BookResponse from(Books books){
        return new BookResponse(
                books.getId(),
                books.getIsbn(),
                books.getTitle(),
                books.getTotalCopies(),
                books.getAvailableCopies()
        );
    }
}
