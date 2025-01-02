package com.dcw.cartfrenzy.controller;

import com.dcw.cartfrenzy.response.ApiResponse;
import com.dcw.cartfrenzy.service.cart.ICartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

;

@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

    private final ICartItemService cartItemService;

    public CartItemController(ICartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/item/{productId}")
    public ResponseEntity<ApiResponse> addItemToCart(@PathVariable Long productId,
                                                     @RequestParam(required = false) Integer quantity) {

        cartItemService.addItemToCart(productId, quantity);

        return ResponseEntity.ok(new ApiResponse("Add Item Success", null));
    }

    @DeleteMapping("cart/item/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long itemId) {

        cartItemService.removeItemFromCart(itemId);

        return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));
    }

    @PutMapping("/cart/item/{itemId}")
    public ResponseEntity<ApiResponse> updateItemQuantity(
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {

        cartItemService.updateItemQuantity(itemId, quantity);

        return ResponseEntity.ok(new ApiResponse("Update Item Success", null));
    }
}
