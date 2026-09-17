
package com.examly.springapp.repository;

import com.examly.springapp.entity.Inventory;
import com.examly.springapp.entity.Product;
import com.examly.springapp.entity.Bin;
import com.examly.springapp.enums.InventoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByProduct(Product product);

    List<Inventory> findByBin(Bin bin);

    List<Inventory> findByStatus(InventoryStatus status);

    List<Inventory> findByBatchNo(String batchNo);

    // FEFO: First Expired First Out
    @Query("SELECT i FROM Inventory i WHERE i.product.id = :productId AND i.status = 'AVAILABLE' AND i.quantity > 0 ORDER BY i.expiryDate ASC")
    List<Inventory> findByProductIdOrderByExpiryDateAsc(@Param("productId") Long productId);

    // Low stock alert
    @Query("SELECT i FROM Inventory i WHERE i.quantity <= :threshold AND i.status = 'AVAILABLE'")
    List<Inventory> findLowStock(@Param("threshold") Integer threshold);

    // Expiring soon
    @Query("SELECT i FROM Inventory i WHERE i.expiryDate <= :date AND i.status = 'AVAILABLE' AND i.quantity > 0")
    List<Inventory> findExpiringSoon(@Param("date") LocalDate date);

    // Total quantity for a product
    @Query("SELECT COALESCE(SUM(i.quantity), 0) FROM Inventory i WHERE i.product.id = :productId AND i.status = 'AVAILABLE'")
    Integer getTotalAvailableQuantity(@Param("productId") Long productId);
}

