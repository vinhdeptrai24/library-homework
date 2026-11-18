package com.example.library.repository;

import com.example.library.entity.Loans;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;
import java.util.Optional;

public interface LoansRepository extends JpaRepository<Loans, Long> {

    long countByMemberIdAndReturnedAtIsNull(Long memberId);

    boolean existsByMemberIdAndBookIdAndReturnedAtIsNull(Long memberId, Long bookId);
    @EntityGraph(attributePaths = "book")
    List<Loans> findByMemberIdAndReturnedAtIsNull(Long memberId);
    @EntityGraph(attributePaths = "book")
    List<Loans> findByMemberId(Long memberId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select l from Loans l where l.id = :id")
    Optional<Loans> findByIdForUpdate(@Param("id") Long id);
}