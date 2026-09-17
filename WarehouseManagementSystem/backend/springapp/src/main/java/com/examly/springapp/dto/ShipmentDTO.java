
package com.examly.springapp.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentDTO {

    private Long orderId;
    private String awbNumber;
    private String carrier;
    private String trackingUrl;
}

