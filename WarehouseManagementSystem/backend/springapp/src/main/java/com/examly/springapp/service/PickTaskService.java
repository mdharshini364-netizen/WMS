
package com.examly.springapp.service;

import com.examly.springapp.dto.PickTaskDTO;
import com.examly.springapp.entity.Bin;
import com.examly.springapp.entity.Orders;
import com.examly.springapp.entity.PickTask;
import com.examly.springapp.entity.Product;
import com.examly.springapp.enums.PickTaskStatus;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.repository.BinRepository;
import com.examly.springapp.repository.OrderRepository;
import com.examly.springapp.repository.PickTaskRepository;
import com.examly.springapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PickTaskService {

    @Autowired
    private PickTaskRepository pickTaskRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BinRepository binRepository;

    public PickTask createPickTask(PickTaskDTO dto) {
        Orders order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", dto.getOrderId().toString()));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", dto.getProductId().toString()));

        Bin bin = binRepository.findById(dto.getBinId())
                .orElseThrow(() -> new ResourceNotFoundException("Bin", "id", dto.getBinId().toString()));

        PickTask pickTask = PickTask.builder()
                .order(order)
                .product(product)
                .bin(bin)
                .quantity(dto.getQuantity())
                .status(PickTaskStatus.PENDING)
                .build();

        return pickTaskRepository.save(pickTask);
    }

    public List<PickTask> getAllPickTasks() {
        return pickTaskRepository.findAll();
    }

    public PickTask getPickTaskById(Long id) {
        return pickTaskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PickTask", "id", id.toString()));
    }

    public List<PickTask> getPickTasksByOrder(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId.toString()));
        return pickTaskRepository.findByOrder(order);
    }

    public List<PickTask> getPickTasksByStatus(String status) {
        PickTaskStatus pickTaskStatus = PickTaskStatus.valueOf(status.toUpperCase());
        return pickTaskRepository.findByStatus(pickTaskStatus);
    }

    public PickTask updatePickTaskStatus(Long id, String status) {
        PickTask pickTask = pickTaskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PickTask", "id", id.toString()));

        PickTaskStatus newStatus = PickTaskStatus.valueOf(status.toUpperCase());
        pickTask.setStatus(newStatus);

        if (newStatus == PickTaskStatus.IN_PROGRESS) {
            pickTask.setPickedAt(LocalDateTime.now());
        } else if (newStatus == PickTaskStatus.COMPLETED) {
            pickTask.setCompletedAt(LocalDateTime.now());
        }

        return pickTaskRepository.save(pickTask);
    }

    public void deletePickTask(Long id) {
        PickTask pickTask = pickTaskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PickTask", "id", id.toString()));
        pickTaskRepository.delete(pickTask);
    }
}

