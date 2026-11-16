package com.example.library.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReturnResponse(
        Long loanId,
        Long bookId,
        LocalDate borrowedAt,
        LocalDate dueDate,
        LocalDate returnedAt,
        long lateDays,
        BigDecimal fee
) {
}