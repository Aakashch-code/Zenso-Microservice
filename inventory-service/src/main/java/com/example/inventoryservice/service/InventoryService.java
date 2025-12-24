package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.InventoryRequestDTO;
import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    // 1. Check stock availability
    public boolean isInStock(String skuCode, Integer requiredQty) {

        return inventoryRepository.findBySkuCode(skuCode)
                .map(inventory -> inventory.getQuantity() >= requiredQty)
                .orElse(false);
    }

    // 2. Add or update inventory
    public void addInventory(InventoryRequestDTO inventoryRequest) {

        Inventory inventory = inventoryRepository
                .findBySkuCode(inventoryRequest.getSkuCode())
                .orElse(new Inventory());

        inventory.setSkuCode(inventoryRequest.getSkuCode());
        inventory.setQuantity(inventoryRequest.getQuantity());

        inventoryRepository.save(inventory);

        log.info(
                "Inventory saved | SKU: {} | Quantity: {}",
                inventoryRequest.getSkuCode(),
                inventoryRequest.getQuantity()
        );
    }
}
