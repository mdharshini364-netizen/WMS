
package com.examly.springapp.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PickCompleteDTO {

    @NotNull(message = "Pick Task ID is required")
    private Long pickTaskId;

    @NotNull(message = "Quantity picked is required")
    @Min(value = 1, message = "Quantity picked must be at least 1")
    private Integer quantityPicked;

    private String notes; // Optional notes about the pick
}

