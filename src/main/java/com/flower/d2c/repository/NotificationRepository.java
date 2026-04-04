package com.flower.d2c.repository;

import com.flower.d2c.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdAndIsReadFalseOrderByCreatedAtDesc(Long userId);
}
