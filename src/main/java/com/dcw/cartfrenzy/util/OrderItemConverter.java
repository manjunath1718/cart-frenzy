package com.dcw.cartfrenzy.util;

import com.dcw.cartfrenzy.dto.OrderItemDto;
import com.dcw.cartfrenzy.model.OrderItem;
import org.modelmapper.ModelMapper;

public class OrderItemConverter {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static OrderItemDto convertToDto(OrderItem orderItem) {
        return modelMapper.map(orderItem, OrderItemDto.class);
    }

}
