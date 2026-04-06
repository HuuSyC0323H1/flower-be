package com.flower.d2c.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "site_configs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiteConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String siteName;

    private String heroSlogan;

    private String heroImage;

    private String featuredIds; // comma-separated product ids

    private String subscriptionIds; // comma-separated subscription ids
}
