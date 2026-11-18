package com.example.library.controller;

import com.example.library.dto.LoanResponse;
import com.example.library.service.LoanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final LoanService loanService;

    public MemberController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/{id}/loans")
    public List<LoanResponse> getLoans(@PathVariable Long id,
                                       @RequestParam(defaultValue = "false") boolean active) {
        return loanService.getMemberLoans(id, active);
    }
}