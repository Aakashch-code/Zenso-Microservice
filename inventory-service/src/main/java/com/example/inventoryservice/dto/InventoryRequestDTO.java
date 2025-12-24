package com.example.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequestDTO {

    private String skuCode;   // e.g. "iphone_15_red"
    private Integer quantity; // e.g. 50
}
