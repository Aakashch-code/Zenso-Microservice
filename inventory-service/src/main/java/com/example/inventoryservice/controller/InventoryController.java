package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.InventoryRequestDTO;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // 1. Check if product is in stock
    // GET /api/inventory/check?skuCode=iphone_15_red&requiredQty=1
    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(
            @RequestParam String skuCode,
            @RequestParam Integer requiredQty
    ) {
        return inventoryService.isInStock(skuCode, requiredQty);
    }

    // 2. Add or update inventory
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addInventory(@RequestBody InventoryRequestDTO inventoryRequest) {
        inventoryService.addInventory(inventoryRequest);
    }
}
