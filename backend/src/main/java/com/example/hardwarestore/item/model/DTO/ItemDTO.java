package com.example.hardwarestore.item.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    @Builder.Default
    private Long id = -1L;

    @Builder.Default
    private String name = "";

    @Builder.Default
    private String details = "";

    @Builder.Default
    private Integer quantity = -1;

    @Builder.Default
    private Float price = -1F;

    @Builder.Default
    private String type = "";
}
