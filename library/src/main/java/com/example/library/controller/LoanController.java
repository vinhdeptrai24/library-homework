package com.example.library.controller;

import com.example.library.dto.BorrowRequest;
import com.example.library.dto.LoanResponse;
import com.example.library.dto.ReturnResponse;
import com.example.library.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanResponse> borrow(@Valid @RequestBody BorrowRequest request) {
        LoanResponse created = loanService.borrow(request);
        return ResponseEntity.created(URI.create("/api/loans/" + created.id())).body(created);
    }

    @PostMapping("/{id}/return")
    public ReturnResponse returnLoan(@PathVariable Long id) {
        return loanService.returnLoan(id);
    }
}