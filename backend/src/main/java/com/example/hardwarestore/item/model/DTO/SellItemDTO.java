package com.example.hardwarestore.item.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellItemDTO {
    @Builder.Default
    private Long id = -1L;

    @Builder.Default
    private Integer quantity = -1;

}
