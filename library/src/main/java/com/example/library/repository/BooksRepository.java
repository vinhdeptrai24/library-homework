package com.example.library.repository;

import com.example.library.entity.Books;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BooksRepository extends JpaRepository<Books, Long> {

    boolean existsByIsbn(String isbn);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Books b where b.id = :id")
    Optional<Books> findByIdForUpdate(@Param("id") Long id);
}