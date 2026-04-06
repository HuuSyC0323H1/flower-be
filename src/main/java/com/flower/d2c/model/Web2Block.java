package com.flower.d2c.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "web2_blocks")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Web2Block extends BaseAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String iconCode;
    private String title;
    private String apiEndpoint;
}
