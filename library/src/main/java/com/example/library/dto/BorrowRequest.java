package com.example.library.dto;

import jakarta.validation.constraints.NotNull;

public record BorrowRequest(
        @NotNull(message = "memberId là bắt buộc") Long memberId,
        @NotNull(message = "bookId là bắt buộc") Long bookId
) {
}