package com.dcw.cartfrenzy.service.order;

import com.dcw.cartfrenzy.dto.OrderItemDto;

import java.util.List;

public interface IOrderItemService {

    List<OrderItemDto> getOrderItems(Long productId);
}
