package com.example.accounting.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "goods")
public class Good {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "item")
    @NotEmpty(message = "Item name should not be empty")
    @Size(min = 2, max = 30, message = "Item name should be between 2 and 30 characters")
//    Как изменить на Not null в БД?
    private String item;

    // Double и numeric не работают вместе?
    @Column(name = "quantity")
    @Min(value = 1, message = "Quantity should be greater than 0")
    private double quantity;

    @Column(name = "price")
    @Min(value = 0, message = "Price should be greater than 0")
    private double price;

    @Column(name = "unit_of_measurement")
    @NotEmpty(message = "Unit of measurement should not be empty")
    private String unitOfMeasurement;

    @Column(name = "category")
    @NotEmpty(message = "Category should not be empty")
    private String category;

    @ManyToMany
    @JoinTable(name ="goods_suppliers",
            joinColumns = @JoinColumn(name ="goods_id"),
    inverseJoinColumns = @JoinColumn(name="suppliers_id"))
    private List<Supplier> suppliers;
}