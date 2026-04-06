package com.flower.d2c.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

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

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    private Integer stockQuantity; // Số lượng bao bó hiện có trong vườn tuần này

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ProductType type; // Tách biệt RETAIL và SUBSCRIPTION_KIT

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    public enum ProductType {
        RETAIL_FLOWER,      // Hoa bán lẻ ngoài gói
        SUBSCRIPTION_KIT,   // Bình, Kéo bán kèm gói
        MERCHANDISE
    }
}
