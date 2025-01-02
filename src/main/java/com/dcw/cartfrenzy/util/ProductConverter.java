package com.dcw.cartfrenzy.util;

import org.modelmapper.ModelMapper;

import com.dcw.cartfrenzy.dto.ProductDto;
import com.dcw.cartfrenzy.model.Product;

public class ProductConverter {
	
	private static final ModelMapper modelMapper = new ModelMapper();

    public static ProductDto convertToDto(Product product) {
    	
        return modelMapper.map(product, ProductDto.class);
    }

	

}
