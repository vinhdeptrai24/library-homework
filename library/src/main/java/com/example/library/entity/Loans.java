package com.example.library.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id")
    private Books book;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Members member;

    @Column(name = "borrowed_at", nullable = false)
    private LocalDate borrowedAt;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "returned_at")
    private LocalDate returnedAt;

    protected Loans() {
    }

    public Loans(Books book, Members member, LocalDate borrowedAt, LocalDate dueDate) {
        this.book = book;
        this.member = member;
        this.borrowedAt = borrowedAt;
        this.dueDate = dueDate;
    }

    public Long getId() { return id; }
    public Books getBook() { return book; }
    public Members getMember() { return member; }
    public LocalDate getBorrowedAt() { return borrowedAt; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnedAt() { return returnedAt; }

    public void setReturnedAt(LocalDate returnedAt) {
        this.returnedAt = returnedAt;
    }
}