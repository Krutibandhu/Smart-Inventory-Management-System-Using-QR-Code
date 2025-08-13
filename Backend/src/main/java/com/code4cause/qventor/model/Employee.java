package com.code4cause.qventor.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String phoneNumber;
    private String companyName;
    private String supabaseUserId;

    // Many employees can belong to one admin
    @ManyToOne
    @JoinColumn(name = "admin_id") // foreign key in employee table
    private Admin admin;

    public Employee(String fullName, String email, String phoneNumber, String companyName, String supabaseUserId, Admin admin) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.supabaseUserId = supabaseUserId;
        this.admin = admin;
    }
}
