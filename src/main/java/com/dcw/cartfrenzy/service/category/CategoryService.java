package com.dcw.cartfrenzy.service.category;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dcw.cartfrenzy.exception.ResourceNotFoundException;
import com.dcw.cartfrenzy.model.Category;
import com.dcw.cartfrenzy.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

	private final CategoryRepository categoryRepository;
	
	@Override
	public Category getCategoryById(Long id) {
		
		return categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found!"));
	}

	@Override
	public Category getCategoryByName(String name) {
		
		return categoryRepository.findByName(name);
	}

	@Override
	public List<Category> getAllCategories() {
		
		return categoryRepository.findAll();
	}

	@Override
	public Category addCategory(Category category) {
		
		return null;
	}

	@Override
	public Category updateCategory(Category category, Long id) {
		
		return null;
	}

	@Override
	public void deleteCategoryById(Long id) {
		
	}

}
