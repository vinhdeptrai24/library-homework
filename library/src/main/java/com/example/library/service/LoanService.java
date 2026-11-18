package com.example.library.service;

import com.example.library.dto.BorrowRequest;
import com.example.library.dto.LoanResponse;
import com.example.library.entity.Books;
import com.example.library.entity.Loans;
import com.example.library.entity.Members;
import com.example.library.exception.BusinessException;
import com.example.library.exception.NotFoundException;
import com.example.library.repository.BooksRepository;
import com.example.library.repository.LoansRepository;
import com.example.library.repository.MembersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.library.dto.ReturnResponse;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private static final int MAX_ACTIVE_LOANS = 3;
    private static final int LOAN_DAYS = 14;
    private static final BigDecimal FEE_PER_DAY = BigDecimal.valueOf(5000);

    private final LoansRepository loansRepository;
    private final BooksRepository booksRepository;
    private final MembersRepository membersRepository;

    public LoanService(LoansRepository loansRepository,
                       BooksRepository booksRepository,
                       MembersRepository membersRepository) {
        this.loansRepository = loansRepository;
        this.booksRepository = booksRepository;
        this.membersRepository = membersRepository;
    }

    @Transactional
    public LoanResponse borrow(BorrowRequest request) {
        Members member = membersRepository.findById(request.memberId())
                .orElseThrow(() -> new NotFoundException("MEMBER_NOT_FOUND",
                        "Không tìm thấy member id = " + request.memberId()));

        Books book = booksRepository.findByIdForUpdate(request.bookId())
                .orElseThrow(() -> new NotFoundException("BOOK_NOT_FOUND",
                        "Không tìm thấy sách id = " + request.bookId()));

        if (loansRepository.existsByMemberIdAndBookIdAndReturnedAtIsNull(member.getId(), book.getId())) {
            throw new BusinessException("ALREADY_BORROWED",
                    "Member id = " + member.getId() + " đang mượn sách id = " + book.getId());
        }

        long activeLoans = loansRepository.countByMemberIdAndReturnedAtIsNull(member.getId());
        if (activeLoans >= MAX_ACTIVE_LOANS) {
            throw new BusinessException("LOAN_LIMIT_EXCEEDED",
                    "Member id = " + member.getId() + " đang mượn " + activeLoans + " cuốn");
        }

        if (book.getAvailableCopies() <= 0) {
            throw new BusinessException("BOOK_NOT_AVAILABLE",
                    "Sách id = " + book.getId() + " đã hết bản");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        LocalDate today = LocalDate.now();
        Loans loan = loansRepository.save(new Loans(book, member, today, today.plusDays(LOAN_DAYS)));

        return LoanResponse.from(loan);
    }
    @Transactional
    public ReturnResponse returnLoan(Long loanId) {
        Loans loan = loansRepository.findByIdForUpdate(loanId)
                .orElseThrow(() -> new NotFoundException("LOAN_NOT_FOUND",
                        "Không tìm thấy lượt mượn id = " + loanId));

        if (loan.getReturnedAt() != null) {
            throw new BusinessException("ALREADY_RETURNED",
                    "Lượt mượn id = " + loanId + " đã được trả");
        }

        Books book = booksRepository.findByIdForUpdate(loan.getBook().getId())
                .orElseThrow(() -> new IllegalStateException("Lượt mượn trỏ tới sách không tồn tại"));

        LocalDate today = LocalDate.now();
        long lateDays = Math.max(0, ChronoUnit.DAYS.between(loan.getDueDate(), today));
        BigDecimal fee = FEE_PER_DAY.multiply(BigDecimal.valueOf(lateDays));

        loan.setReturnedAt(today);
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        return new ReturnResponse(
                loan.getId(),
                book.getId(),
                loan.getBorrowedAt(),
                loan.getDueDate(),
                today,
                lateDays,
                fee);
    }
    @Transactional(readOnly = true)
    public List<LoanResponse> getMemberLoans(Long memberId, boolean activeOnly) {
        if (!membersRepository.existsById(memberId)) {
            throw new NotFoundException("MEMBER_NOT_FOUND",
                    "Không tìm thấy member id = " + memberId);
        }

        List<Loans> loans = activeOnly
                ? loansRepository.findByMemberIdAndReturnedAtIsNull(memberId)
                : loansRepository.findByMemberId(memberId);

        return loans.stream().map(LoanResponse::from).toList();
    }
}