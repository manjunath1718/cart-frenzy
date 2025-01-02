package com.dcw.cartfrenzy.util;

import com.dcw.cartfrenzy.dto.OrderDto;
import com.dcw.cartfrenzy.model.Order;
import org.modelmapper.ModelMapper;

public class OrderConverter {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

}
