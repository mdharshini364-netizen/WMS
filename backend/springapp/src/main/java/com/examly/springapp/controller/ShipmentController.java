
package com.examly.springapp.controller;

import com.examly.springapp.dto.ShipmentDTO;
import com.examly.springapp.entity.Shipment;
import com.examly.springapp.enums.ShipmentStatus;
import com.examly.springapp.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    // POST /api/shipments
    @PostMapping
    public ResponseEntity<Shipment> createShipment(@RequestBody ShipmentDTO dto) {
        Shipment created = shipmentService.createShipment(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET /api/shipments
    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments() {
        return ResponseEntity.ok(shipmentService.getAllShipments());
    }

    // GET /api/shipments/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable Long id) {
        return ResponseEntity.ok(shipmentService.getShipmentById(id));
    }

    // GET /api/shipments/order/{orderId}
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Shipment> getShipmentByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(shipmentService.getShipmentByOrderId(orderId));
    }

    // GET /api/shipments/awb/{awbNumber}
    @GetMapping("/awb/{awbNumber}")
    public ResponseEntity<Shipment> getShipmentByAwb(@PathVariable String awbNumber) {
        return ResponseEntity.ok(shipmentService.getShipmentByAwb(awbNumber));
    }

    // GET /api/shipments/status/{status}
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Shipment>> getShipmentsByStatus(@PathVariable String status) {
        ShipmentStatus shipmentStatus = ShipmentStatus.valueOf(status.toUpperCase());
        return ResponseEntity.ok(shipmentService.getShipmentsByStatus(shipmentStatus));
    }

    // GET /api/shipments/carrier/{carrier}
    @GetMapping("/carrier/{carrier}")
    public ResponseEntity<List<Shipment>> getShipmentsByCarrier(@PathVariable String carrier) {
        return ResponseEntity.ok(shipmentService.getShipmentsByCarrier(carrier));
    }

    // PUT /api/shipments/{id}/dispatch
    @PutMapping("/{id}/dispatch")
    public ResponseEntity<Shipment> dispatchShipment(@PathVariable Long id) {
        return ResponseEntity.ok(shipmentService.dispatchShipment(id));
    }

    // PUT /api/shipments/{id}/delivered
    @PutMapping("/{id}/delivered")
    public ResponseEntity<Shipment> markDelivered(@PathVariable Long id) {
        return ResponseEntity.ok(shipmentService.markDelivered(id));
    }

    // DELETE /api/shipments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShipment(@PathVariable Long id) {
        shipmentService.deleteShipment(id);
        return ResponseEntity.ok("Shipment deleted successfully");
    }
}

