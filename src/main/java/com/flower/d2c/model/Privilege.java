package com.flower.d2c.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Table(name = "privileges")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Ví dụ: "READ_REPORTS", "PAUSE_ANY_DELIVERY", "EDIT_PLANS"

    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles;
}
