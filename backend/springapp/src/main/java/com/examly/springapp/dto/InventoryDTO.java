
package com.examly.springapp.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDTO {

    private Long id;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Bin ID is required")
    private Long binId;

    private String batchNo;

    private LocalDate expiryDate;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    private String status; // AVAILABLE, RESERVED, QUARANTINE, DAMAGED

    // Read-only fields for response
    private String productName;
    private String productSku;
    private String binZone;
}

