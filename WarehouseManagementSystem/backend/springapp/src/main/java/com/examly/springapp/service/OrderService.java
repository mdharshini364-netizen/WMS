
package com.examly.springapp.service;

import com.examly.springapp.dto.OrderDTO;
import com.examly.springapp.entity.Orders;
import com.examly.springapp.enums.OrderStatus;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Orders createOrder(OrderDTO dto) {
        Orders order = Orders.builder()
                .orderNumber(dto.getOrderNumber() != null ? dto.getOrderNumber() : "ORD-" + System.currentTimeMillis())
                .customerName(dto.getCustomerName())
                .customerEmail(dto.getCustomerEmail())
                .totalItems(dto.getTotalItems())
                .priority(dto.getPriority())
                .notes(dto.getNotes())
                .status(OrderStatus.PENDING)
                .orderDate(LocalDate.now())
                .build();


        return orderRepository.save(order);
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Orders getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id.toString()));
    }

    public Orders getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderNumber", orderNumber));
    }

    public List<Orders> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public List<Orders> getPendingOrdersByPriority() {
        return orderRepository.findByStatusOrderByPriorityAsc(OrderStatus.PENDING);
    }

    public List<Orders> searchByCustomerName(String customerName) {
        return orderRepository.findByCustomerNameContainingIgnoreCase(customerName);
    }

    public List<Orders> getOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
        return orderRepository.findByCreatedAtBetween(
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59));
    }

    public Orders updateOrder(Long id, OrderDTO dto) {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id.toString()));

        order.setCustomerName(dto.getCustomerName());
        order.setCustomerEmail(dto.getCustomerEmail());
        order.setTotalItems(dto.getTotalItems());
        order.setPriority(dto.getPriority());
        order.setNotes(dto.getNotes());

        return orderRepository.save(order);
    }

    public Orders updateOrderStatus(Long id, String status) {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id.toString()));

        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        order.setStatus(orderStatus);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id.toString()));
        orderRepository.delete(order);
    }
}

