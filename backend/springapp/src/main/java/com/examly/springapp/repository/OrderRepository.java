
package com.examly.springapp.repository;

import com.examly.springapp.entity.Orders;
import com.examly.springapp.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByOrderNumber(String orderNumber);
    List<Orders> findByStatus(OrderStatus status);
    List<Orders> findByStatusOrderByPriorityAsc(OrderStatus status);
    List<Orders> findByCustomerNameContainingIgnoreCase(String customerName);
    List<Orders> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}

