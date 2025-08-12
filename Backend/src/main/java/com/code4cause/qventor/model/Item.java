package com.code4cause.qventor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "item")
@Getter
@Setter
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private int quantity;
    private double exportAmount;
    private double importAmount;
    private LocalDate recentExportDate;
    private LocalDate recentImportDate;

    // Link to the Admin who owns this item
    @ManyToOne
    @JoinColumn(name = "admin_id")
    @JsonIgnore
    private Admin admin;

    public Item(String name, String description, double price, int quantity, double exportAmount, double importAmount, LocalDate recentExportDate, LocalDate recentImportDate, Admin admin) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.exportAmount = exportAmount;
        this.importAmount = importAmount;
        this.recentExportDate = recentExportDate;
        this.recentImportDate = recentImportDate;
        this.admin = admin;
    }
}
