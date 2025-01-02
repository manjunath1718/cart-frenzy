package com.dcw.cartfrenzy.service.order;

import com.dcw.cartfrenzy.dto.OrderItemDto;
import com.dcw.cartfrenzy.model.OrderItem;
import com.dcw.cartfrenzy.repository.OrderItemRepository;
import com.dcw.cartfrenzy.util.OrderItemConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService implements IOrderItemService {

    private OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public List<OrderItemDto> getOrderItems(Long productId) {

        return orderItemRepository.findByProductId(productId)
                .stream()
                .map(this::convertToDto)
                .toList();

    }

    private OrderItemDto convertToDto(OrderItem orderItem) {
        return OrderItemConverter.convertToDto(orderItem);
    }
}
