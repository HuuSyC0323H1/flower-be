package com.flower.d2c.repository;

import com.flower.d2c.model.DeliverySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface DeliveryScheduleRepository extends JpaRepository<DeliverySchedule, Long> {
    List<DeliverySchedule> findBySubscriptionIdOrderByWeekNumberAsc(Long subscriptionId);
    List<DeliverySchedule> findByExpectedDeliveryDateAndStatus(LocalDate date, DeliverySchedule.DeliveryStatus status);
}
