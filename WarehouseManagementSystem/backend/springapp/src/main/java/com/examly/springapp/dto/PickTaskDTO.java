
package com.examly.springapp.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PickTaskDTO {

    private Long orderId;
    private Long productId;
    private Long binId;
    private Integer quantity;
}

