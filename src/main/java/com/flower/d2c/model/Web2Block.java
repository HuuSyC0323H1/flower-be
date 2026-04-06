package com.flower.d2c.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "web2_blocks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Web2Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "LONGTEXT")
    private String icon; // For image, emoji or base64

    private String description;

    private String apiLink;
}
