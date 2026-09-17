
package com.examly.springapp.service;

import com.examly.springapp.dto.InventoryDTO;
import com.examly.springapp.entity.Bin;
import com.examly.springapp.entity.Inventory;
import com.examly.springapp.entity.Product;
import com.examly.springapp.enums.InventoryStatus;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.repository.BinRepository;
import com.examly.springapp.repository.InventoryRepository;
import com.examly.springapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BinRepository binRepository;

    public Inventory createInventory(InventoryDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", dto.getProductId().toString()));

        Bin bin = binRepository.findById(dto.getBinId())
                .orElseThrow(() -> new ResourceNotFoundException("Bin", "id", dto.getBinId().toString()));

        Inventory inventory = Inventory.builder()
                .product(product)
                .bin(bin)
                .quantity(dto.getQuantity())
                .batchNo(dto.getBatchNo())
                .expiryDate(dto.getExpiryDate())
                .status(InventoryStatus.AVAILABLE)
                .build();

        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "id", id.toString()));
    }

    public List<Inventory> getInventoryByProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId.toString()));
        return inventoryRepository.findByProduct(product);
    }

    public List<Inventory> getLowStock(Integer threshold) {
        return inventoryRepository.findLowStock(threshold);
    }

    public List<Inventory> getExpiringSoon(Integer days) {
        LocalDate date = LocalDate.now().plusDays(days);
        return inventoryRepository.findExpiringSoon(date);
    }

    public List<Inventory> getByFefo(Long productId) {
        return inventoryRepository.findByProductIdOrderByExpiryDateAsc(productId);
    }

    public Integer getTotalAvailableQuantity(Long productId) {
        return inventoryRepository.getTotalAvailableQuantity(productId);
    }
    public List<Inventory> getInventoryByStatus(InventoryStatus status) {
    return inventoryRepository.findByStatus(status);
    }


    public Inventory updateInventory(Long id, InventoryDTO dto) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "id", id.toString()));

        inventory.setQuantity(dto.getQuantity());
        inventory.setBatchNo(dto.getBatchNo());
        inventory.setExpiryDate(dto.getExpiryDate());

        if (dto.getStatus() != null) {
            inventory.setStatus(InventoryStatus.valueOf(dto.getStatus().toUpperCase()));
        }

        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", dto.getProductId().toString()));
            inventory.setProduct(product);
        }

        if (dto.getBinId() != null) {
            Bin bin = binRepository.findById(dto.getBinId())
                    .orElseThrow(() -> new ResourceNotFoundException("Bin", "id", dto.getBinId().toString()));
            inventory.setBin(bin);
        }

        return inventoryRepository.save(inventory);
    }

    public void deleteInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "id", id.toString()));
        inventoryRepository.delete(inventory);
    }
}

