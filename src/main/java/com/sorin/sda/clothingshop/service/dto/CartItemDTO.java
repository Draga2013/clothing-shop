package com.sorin.sda.clothingshop.service.dto;

public class CartItemDTO {
    private Long productId;
    private Integer quantity;
    private String size;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItemModel{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
