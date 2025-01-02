package com.dcw.cartfrenzy.controller;

import com.dcw.cartfrenzy.dto.OrderItemDto;
import com.dcw.cartfrenzy.response.ApiResponse;
import com.dcw.cartfrenzy.service.order.IOrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orderItems")
public class OrderItemController {

    private final IOrderItemService orderItemService;

    public OrderItemController(IOrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/order/product/{productId}")
    public ResponseEntity<ApiResponse> getOrderItems(@PathVariable Long productId) {

        List<OrderItemDto> orders = orderItemService.getOrderItems(productId);

        return ResponseEntity.ok(new ApiResponse("success", orders));
    }
}
