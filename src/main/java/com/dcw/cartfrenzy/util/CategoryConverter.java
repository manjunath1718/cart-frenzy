package com.dcw.cartfrenzy.util;

import org.modelmapper.ModelMapper;

import com.dcw.cartfrenzy.dto.CategoryDto;
import com.dcw.cartfrenzy.model.Category;

public class CategoryConverter {
	
	private static final ModelMapper modelMapper = new ModelMapper();

    public static CategoryDto convertToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    public static Category convertToEntity(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }
}
