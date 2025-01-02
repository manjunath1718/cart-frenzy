package com.dcw.cartfrenzy.controller;

import com.dcw.cartfrenzy.dto.OrderDto;
import com.dcw.cartfrenzy.response.ApiResponse;
import com.dcw.cartfrenzy.service.order.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {

        OrderDto order = orderService.placeOrder(userId);

        return ResponseEntity.ok(new ApiResponse("Order placed successfully", order));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> getOrder(@PathVariable Long orderId) {

        OrderDto order = orderService.getOrder(orderId);

        return ResponseEntity.ok(new ApiResponse("Item Order success", order));
    }

    @GetMapping("/orders{userId}/")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {

        List<OrderDto> orders = orderService.getUserOrders(userId);

        return ResponseEntity.ok(new ApiResponse("User Orders", orders));
    }


}
