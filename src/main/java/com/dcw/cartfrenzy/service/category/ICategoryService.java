package com.dcw.cartfrenzy.service.category;

import java.util.List;

import com.dcw.cartfrenzy.dto.CategoryDto;
import com.dcw.cartfrenzy.model.Category;

public interface ICategoryService {

	CategoryDto getCategoryById(Long id);
    CategoryDto getCategoryByName(String name);
    List<CategoryDto> getAllCategories();
    CategoryDto addCategory(Category category);
    CategoryDto updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);
}
