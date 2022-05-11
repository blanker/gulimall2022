package com.blank.ecommerce.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsMessage {
    private Long userId;
    private Long orderId;
    private Long addressId;
    private String extraInfo;
}
