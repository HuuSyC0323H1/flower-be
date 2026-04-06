package com.flower.d2c.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "privileges")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Privilege extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String resource; // e.g., "product", "order"

    @Column(nullable = false)
    private String action; // e.g., "read", "write", "delete"

    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles;
}
