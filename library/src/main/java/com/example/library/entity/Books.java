package com.example.library.entity;

import jakarta.persistence.*;

@Entity
@Table(name="books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="isbn",nullable = false,unique = true)
    private String isbn;
    @Column(name="title",nullable = false)
    private String title;
    @Column(name="total_copies",nullable = false)
    private Integer total_copies;
    @Column(name="available_copies",nullable = false)
    private Integer available_copies;
    protected Books(){
    }
    public Books(String isbn,String title,Integer total_copies){
        this.isbn=isbn;
        this.title=title;
        this.total_copies=total_copies;
        this.available_copies=total_copies;
    }
    public long getId() {return id;
    }
    public String getIsbn() {return isbn;
    }
    public String getTitle() {return title;
    }
    public Integer getTotalCopies() {return total_copies;
    }
    public Integer getAvailableCopies() {return available_copies;
    }

    public void setAvailableCopies(Integer available_copies) {
        this.available_copies = available_copies;
    }
}
