package com.dcw.cartfrenzy.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class CartDto {

    private Long cartId;

    private Set<CartItemDto> cartItems = new HashSet<>();

    private BigDecimal totalAmount;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Set<CartItemDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItemDto> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
