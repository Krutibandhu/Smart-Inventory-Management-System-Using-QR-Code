package com.code4cause.qventor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    public Item(String name, String description, double price, int quantity, double exportAmount, double importAmount, LocalDate recentExportDate, LocalDate recentImportDate) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.exportAmount = exportAmount;
        this.importAmount = importAmount;
        this.recentExportDate = recentExportDate;
        this.recentImportDate = recentImportDate;
    }
}
