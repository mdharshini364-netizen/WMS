
package com.examly.springapp.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long orderId;
    private String orderNumber;
    private String customerName;
    private String customerEmail;
    private Integer totalItems;
    private String priority;
    private String notes;
    private String status;
}

