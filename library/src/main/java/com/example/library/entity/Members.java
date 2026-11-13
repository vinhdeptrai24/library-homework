package com.example.library.entity;

import jakarta.persistence.*;

@Entity
@Table(name="members")
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="email",nullable = false,unique = true)
    private String email;
    @Column(name="name",nullable = false)
    private String name;
    protected Members(){
    }
    public Members(String email,String name){
        this.email=email;
        this.name=name;
    }

    public String getEmail() {return email;
    }

    public String getName() {return name;
    }

    public long getId() {return id;
    }
}
