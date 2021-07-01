package com.example.hardwarestore.payment.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private String name;
    private int quantity;
    private double price;
    private Long user_id;
    private Long item_id;

}
