
package com.examly.springapp.controller;

import com.examly.springapp.entity.Bin;
import com.examly.springapp.service.BinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bins")
public class BinController {

    @Autowired
    private BinService binService;

    // POST /api/bins
    @PostMapping
    public ResponseEntity<Bin> createBin(@RequestBody Bin bin) {
        Bin created = binService.createBin(bin);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET /api/bins
    @GetMapping
    public ResponseEntity<List<Bin>> getAllBins() {
        return ResponseEntity.ok(binService.getAllBins());
    }

    // GET /api/bins/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Bin> getBinById(@PathVariable Long id) {
        return ResponseEntity.ok(binService.getBinById(id));
    }

    // GET /api/bins/code/{binCode}
    @GetMapping("/code/{binCode}")
    public ResponseEntity<Bin> getBinByCode(@PathVariable String binCode) {
        return ResponseEntity.ok(binService.getBinByCode(binCode));
    }

    // GET /api/bins/zone/{zone}
    @GetMapping("/zone/{zone}")
    public ResponseEntity<List<Bin>> getBinsByZone(@PathVariable String zone) {
        return ResponseEntity.ok(binService.getBinsByZone(zone));
    }

    // GET /api/bins/available
    @GetMapping("/available")
    public ResponseEntity<List<Bin>> getAvailableBins() {
        return ResponseEntity.ok(binService.getAvailableBins());
    }

    // PUT /api/bins/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Bin> updateBin(@PathVariable Long id, @RequestBody Bin bin) {
        return ResponseEntity.ok(binService.updateBin(id, bin));
    }

    // DELETE /api/bins/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBin(@PathVariable Long id) {
        binService.deleteBin(id);
        return ResponseEntity.ok("Bin deleted successfully");
    }
}

