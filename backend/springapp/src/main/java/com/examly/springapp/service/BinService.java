
package com.examly.springapp.service;

import com.examly.springapp.entity.Bin;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.repository.BinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BinService {

    @Autowired
    private BinRepository binRepository;

    public Bin createBin(Bin bin) {
        return binRepository.save(bin);
    }

    public List<Bin> getAllBins() {
        return binRepository.findAll();
    }

    public Bin getBinById(Long id) {
        return binRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bin", "id", id.toString()));
    }

    public Bin getBinByCode(String binCode) {
        return binRepository.findByBinCode(binCode)
                .orElseThrow(() -> new ResourceNotFoundException("Bin", "binCode", binCode));
    }

    public List<Bin> getBinsByZone(String zone) {
        return binRepository.findByZone(zone);
    }

    public List<Bin> getAvailableBins() {
        return binRepository.findByOccupied(false);
    }

    public Bin updateBin(Long id, Bin binDetails) {
        Bin bin = binRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bin", "id", id.toString()));

        bin.setBinCode(binDetails.getBinCode());
        bin.setZone(binDetails.getZone());
        bin.setAisle(binDetails.getAisle());
        bin.setRack(binDetails.getRack());
        bin.setLevel(binDetails.getLevel());
        bin.setCapacity(binDetails.getCapacity());

        return binRepository.save(bin);
    }

    public void deleteBin(Long id) {
        Bin bin = binRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bin", "id", id.toString()));
        binRepository.delete(bin);
    }
}

