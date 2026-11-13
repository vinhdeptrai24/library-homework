package com.example.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBookRequest(
        @NotBlank(message = "isbn không được để trống")
        String isbn,

        @NotBlank(message = "title không được để trống")
        String title,

        @NotNull(message = "totalCopies là bắt buộc")
        @Min(value = 1, message = "totalCopies phải từ 1 trở lên")
        Integer totalCopies
)
{}