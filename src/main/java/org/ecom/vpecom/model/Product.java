package org.ecom.vpecom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    private String productName;
    private double price;
    private String image;
    private String description;
    private int quantity;
    private double discount;
    private double specialPrice;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
}
