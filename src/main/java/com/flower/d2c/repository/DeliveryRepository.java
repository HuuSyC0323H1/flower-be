package com.flower.d2c.repository;

import com.flower.d2c.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByUserSubscriptionIdOrderByWeekNumberAsc(Long userSubscriptionId);
    List<Delivery> findByUserSubscriptionIdOrderByScheduledDateAsc(Long userSubscriptionId);
}
