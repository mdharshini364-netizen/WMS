
package com.examly.springapp.repository;

import com.examly.springapp.entity.Bin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BinRepository extends JpaRepository<Bin, Long> {
    Optional<Bin> findByBinCode(String binCode);
    List<Bin> findByZone(String zone);
    List<Bin> findByOccupied(Boolean occupied);
}

