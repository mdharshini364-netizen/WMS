
package com.examly.springapp.controller;

import com.examly.springapp.dto.OrderDTO;
import com.examly.springapp.entity.Orders;
import com.examly.springapp.enums.OrderStatus;
import com.examly.springapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // POST /api/orders
    @PostMapping
    public ResponseEntity<Orders> createOrder(@RequestBody OrderDTO dto) {
        Orders created = orderService.createOrder(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET /api/orders
    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // GET /api/orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // GET /api/orders/status/{status}
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Orders>> getOrdersByStatus(@PathVariable String status) {
        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        return ResponseEntity.ok(orderService.getOrdersByStatus(orderStatus));
    }

    // GET /api/orders/pending-priority
    @GetMapping("/pending-priority")
    public ResponseEntity<List<Orders>> getPendingOrdersByPriority() {
        return ResponseEntity.ok(orderService.getPendingOrdersByPriority());
    }

    // GET /api/orders/search?customerName=xxx
    @GetMapping("/search")
    public ResponseEntity<List<Orders>> searchOrders(@RequestParam String customerName) {
        return ResponseEntity.ok(orderService.searchByCustomerName(customerName));
    }

    // GET /api/orders/date-range?startDate=xxx&endDate=xxx
    @GetMapping("/date-range")
    public ResponseEntity<List<Orders>> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(orderService.getOrdersByDateRange(startDate, endDate));
    }

    // PUT /api/orders/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @RequestBody OrderDTO dto) {
        return ResponseEntity.ok(orderService.updateOrder(id, dto));
    }

    // PUT /api/orders/{id}/status?status=PROCESSING
    @PutMapping("/{id}/status")
    public ResponseEntity<Orders> updateOrderStatus(
            @PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    // DELETE /api/orders/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully");
    }
}

