
package com.examly.springapp.service;

import com.examly.springapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DashboardService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        // Counts
        stats.put("totalProducts", productRepository.count());
        stats.put("totalInventory", inventoryRepository.count());
        stats.put("totalOrders", orderRepository.count());
        stats.put("totalShipments", shipmentRepository.count());
        stats.put("totalUsers", userRepository.count());

        // All records for frontend to calculate breakdowns
        stats.put("orders", orderRepository.findAll());
        stats.put("shipments", shipmentRepository.findAll());
        stats.put("inventory", inventoryRepository.findAll());
        stats.put("recentOrders", orderRepository.findAll());

        return stats;
    }
}

