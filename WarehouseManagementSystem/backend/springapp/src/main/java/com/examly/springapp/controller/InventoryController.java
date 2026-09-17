
package com.examly.springapp.controller;

import com.examly.springapp.dto.InventoryDTO;
import com.examly.springapp.entity.Inventory;
import com.examly.springapp.enums.InventoryStatus;
import com.examly.springapp.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // POST /api/inventory
    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody InventoryDTO dto) {
        Inventory created = inventoryService.createInventory(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET /api/inventory
    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }
    // GET /api/inventory/status/{status}
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Inventory>> getInventoryByStatus(@PathVariable String status) {
         InventoryStatus inventoryStatus = InventoryStatus.valueOf(status.toUpperCase());
        return ResponseEntity.ok(inventoryService.getInventoryByStatus(inventoryStatus));
    }

    // GET /api/inventory/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    // GET /api/inventory/product/{productId}
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Inventory>> getInventoryByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getInventoryByProduct(productId));
    }

    // GET /api/inventory/low-stock?threshold=10
    @GetMapping("/low-stock")
    public ResponseEntity<List<Inventory>> getLowStock(@RequestParam(defaultValue = "10") Integer threshold) {
        return ResponseEntity.ok(inventoryService.getLowStock(threshold));
    }

    // GET /api/inventory/expiring-soon?days=30
    @GetMapping("/expiring-soon")
    public ResponseEntity<List<Inventory>> getExpiringSoon(@RequestParam(defaultValue = "30") Integer days) {
        return ResponseEntity.ok(inventoryService.getExpiringSoon(days));
    }

    // GET /api/inventory/fefo/{productId}
    @GetMapping("/fefo/{productId}")
    public ResponseEntity<List<Inventory>> getByFefo(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getByFefo(productId));
    }

    // GET /api/inventory/total-available/{productId}
    @GetMapping("/total-available/{productId}")
    public ResponseEntity<Integer> getTotalAvailable(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getTotalAvailableQuantity(productId));
    }

    // PUT /api/inventory/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody InventoryDTO dto) {
        return ResponseEntity.ok(inventoryService.updateInventory(id, dto));
    }

    // DELETE /api/inventory/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.ok("Inventory deleted successfully");
    }
}

