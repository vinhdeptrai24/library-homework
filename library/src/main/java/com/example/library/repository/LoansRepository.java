package com.example.library.repository;

import com.example.library.entity.Loans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoansRepository extends JpaRepository<Loans, Long> {

    long countByMemberIdAndReturnedAtIsNull(Long memberId);

    boolean existsByMemberIdAndBookIdAndReturnedAtIsNull(Long memberId, Long bookId);

    List<Loans> findByMemberIdAndReturnedAtIsNull(Long memberId);

    List<Loans> findByMemberId(Long memberId);
}