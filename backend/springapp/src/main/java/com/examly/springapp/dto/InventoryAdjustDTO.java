
package com.examly.springapp.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryAdjustDTO {

    @NotNull(message = "Inventory ID is required")
    private Long inventoryId;

    @NotNull(message = "Adjustment quantity is required")
    private Integer adjustmentQuantity; // Positive = add, Negative = subtract

    @NotBlank(message = "Reason is required")
    private String reason; // DAMAGE, CYCLE_COUNT, RETURN, CORRECTION

    private String newStatus; // Optional: change status during adjustment
}

