package com.dcw.cartfrenzy.service.order;

import com.dcw.cartfrenzy.dto.OrderDto;
import com.dcw.cartfrenzy.model.Order;

import java.util.List;

public interface IOrderService {

    OrderDto placeOrder(Long userId);

    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);


}
