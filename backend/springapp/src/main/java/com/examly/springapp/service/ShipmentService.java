
package com.examly.springapp.service;

import com.examly.springapp.dto.ShipmentDTO;
import com.examly.springapp.entity.Orders;
import com.examly.springapp.entity.Shipment;
import com.examly.springapp.enums.ShipmentStatus;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.repository.OrderRepository;
import com.examly.springapp.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Shipment createShipment(ShipmentDTO dto) {
        Orders order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", dto.getOrderId().toString()));

        Shipment shipment = Shipment.builder()
                .order(order)
                .awbNumber(dto.getAwbNumber())
                .carrier(dto.getCarrier())
                .trackingUrl(dto.getTrackingUrl())
                .status(ShipmentStatus.PACKED)
                .build();

        return shipmentRepository.save(shipment);
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Shipment getShipmentById(Long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment", "id", id.toString()));
    }

    public Shipment getShipmentByOrderId(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId.toString()));
        return shipmentRepository.findByOrder(order)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment", "orderId", orderId.toString()));
    }

    public Shipment getShipmentByAwb(String awbNumber) {
        return shipmentRepository.findByAwbNumber(awbNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment", "awbNumber", awbNumber));
    }

    public List<Shipment> getShipmentsByStatus(ShipmentStatus status) {
        return shipmentRepository.findByStatus(status);
    }

    public List<Shipment> getShipmentsByCarrier(String carrier) {
        return shipmentRepository.findByCarrier(carrier);
    }

    public Shipment dispatchShipment(Long id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment", "id", id.toString()));

        shipment.setStatus(ShipmentStatus.DISPATCHED);
        shipment.setShippedAt(LocalDateTime.now());

        return shipmentRepository.save(shipment);
    }

    public Shipment markDelivered(Long id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment", "id", id.toString()));

        shipment.setStatus(ShipmentStatus.DELIVERED);
        shipment.setDeliveredAt(LocalDateTime.now());

        return shipmentRepository.save(shipment);
    }

    public Shipment updateShipmentStatus(Long id, ShipmentStatus status) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment", "id", id.toString()));

        shipment.setStatus(status);

        if (status == ShipmentStatus.DISPATCHED) {
            shipment.setShippedAt(LocalDateTime.now());
        } else if (status == ShipmentStatus.DELIVERED) {
            shipment.setDeliveredAt(LocalDateTime.now());
        }

        return shipmentRepository.save(shipment);
    }

    public void deleteShipment(Long id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment", "id", id.toString()));
        shipmentRepository.delete(shipment);
    }
}

