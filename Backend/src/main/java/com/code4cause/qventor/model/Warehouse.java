package com.code4cause.qventor.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String warehouseName;
    private String location;
    private boolean enabled;

    public Warehouse(String warehouseName, String location, boolean enabled) {
        this.warehouseName = warehouseName;
        this.location = location;
        this.enabled = enabled;
    }
}
