
package com.examly.springapp.repository;

import com.examly.springapp.entity.Orders;
import com.examly.springapp.entity.Shipment;
import com.examly.springapp.enums.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Optional<Shipment> findByOrder(Orders order);
    Optional<Shipment> findByAwbNumber(String awbNumber);
    List<Shipment> findByStatus(ShipmentStatus status);
    List<Shipment> findByCarrier(String carrier);
}

