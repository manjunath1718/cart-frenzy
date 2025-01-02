package com.dcw.cartfrenzy.util;

import org.modelmapper.ModelMapper;

import com.dcw.cartfrenzy.dto.CartDto;
import com.dcw.cartfrenzy.model.Cart;

public class CartConverter {
	
	private static final ModelMapper modelMapper = new ModelMapper();

    public static CartDto convertToDto(Cart cart) {
    	
        return modelMapper.map(cart, CartDto.class);
    }

}
