package com.flower.d2c.repository;

import com.flower.d2c.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByUserIdOrderByCreatedAtDesc(Long userId);
    Invoice findByTransactionRef(String transactionRef);
}
