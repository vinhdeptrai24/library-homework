package com.example.library.dto;

import com.example.library.entity.Loans;

import java.time.LocalDate;

public record LoanResponse(
        Long id,
        Long bookId,
        String bookTitle,
        Long memberId,
        LocalDate borrowedAt,
        LocalDate dueDate,
        LocalDate returnedAt
) {
    public static LoanResponse from(Loans loan) {
        return new LoanResponse(
                loan.getId(),
                loan.getBook().getId(),
                loan.getBook().getTitle(),
                loan.getMember().getId(),
                loan.getBorrowedAt(),
                loan.getDueDate(),
                loan.getReturnedAt());
    }
}