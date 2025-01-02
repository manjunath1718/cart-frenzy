package com.dcw.cartfrenzy.controller;

import com.dcw.cartfrenzy.dto.CartDto;
import com.dcw.cartfrenzy.response.ApiResponse;
import com.dcw.cartfrenzy.service.cart.ICartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

;

@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {

    private final ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("{cartId}/cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {

        CartDto cart = cartService.getCartDto(cartId);

        return ResponseEntity.ok(new ApiResponse("success", cart));
    }

    @GetMapping("/my-cart")
    public ResponseEntity<ApiResponse> getCart() {

        CartDto cart = cartService.getCart();

        return ResponseEntity.ok(new ApiResponse("success", cart));
    }

    @DeleteMapping("/cart")
    public ResponseEntity<ApiResponse> clearCart() {

        cartService.clearCart();

        return ResponseEntity.ok(new ApiResponse("clear cart success!", null));
    }

    @GetMapping("/cart/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount() {

        BigDecimal totalPrice = cartService.getTotalPrice();

        return ResponseEntity.ok(new ApiResponse("TotalPrice : ", totalPrice));
    }
}
