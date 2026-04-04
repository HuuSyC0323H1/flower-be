package com.flower.d2c.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double price;

    private Integer stockQuantity; // Số lượng bao bó hiện có trong vườn tuần này

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ProductType type; // Tách biệt RETAIL và SUBSCRIPTION_KIT

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public enum ProductType {
        RETAIL_FLOWER,      // Hoa bán lẻ ngoài gói
        SUBSCRIPTION_KIT,   // Bình, Kéo bán kèm gói
        MERCHANDISE
    }
}
