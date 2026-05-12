package com.inventory.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class Movement {
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime timestamp;

    private String sku;
    private String movementType;
    private Integer quantity;

    public Movement() {}

    public Movement(String id, LocalDateTime timestamp, String sku, String movementType, Integer quantity) {
        this.id = id;
        this.timestamp = timestamp;
        this.sku = sku;
        this.movementType = movementType;
        this.quantity = quantity;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getMovementType() { return movementType; }
    public void setMovementType(String movementType) { this.movementType = movementType; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}