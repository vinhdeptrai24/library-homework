package com.example.library.service;

import com.example.library.dto.BookResponse;
import com.example.library.dto.CreateBookRequest;
import com.example.library.entity.Books;
import com.example.library.exception.BusinessException;
import com.example.library.exception.NotFoundException;
import com.example.library.repository.BooksRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BooksRepository booksRepository;

    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Transactional
    public BookResponse create(CreateBookRequest request) {
        if (booksRepository.existsByIsbn(request.isbn())) {
            throw isbnExists(request.isbn());
        }

        Books book = new Books(request.isbn(), request.title(), request.totalCopies());
        try {
            return BookResponse.from(booksRepository.saveAndFlush(book));
        } catch (DataIntegrityViolationException ex) {
            // Hai request trùng ISBN cùng lúc: cả hai cùng qua existsByIsbn,
            // nhưng ràng buộc UNIQUE trong DB chỉ cho một bên thắng.
            throw isbnExists(request.isbn());
        }
    }

    @Transactional(readOnly = true)
    public BookResponse getById(Long id) {
        Books book = booksRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BOOK_NOT_FOUND",
                        "Không tìm thấy sách id = " + id));
        return BookResponse.from(book);
    }

    private BusinessException isbnExists(String isbn) {
        return new BusinessException("ISBN_ALREADY_EXISTS", "ISBN " + isbn + " đã tồn tại");
    }
}