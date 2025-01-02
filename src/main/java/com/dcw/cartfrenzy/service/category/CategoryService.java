package com.dcw.cartfrenzy.service.category;

import com.dcw.cartfrenzy.dto.CategoryDto;
import com.dcw.cartfrenzy.exception.AlreadyExistsException;
import com.dcw.cartfrenzy.exception.ResourceNotFoundException;
import com.dcw.cartfrenzy.model.Category;
import com.dcw.cartfrenzy.repository.CategoryRepository;
import com.dcw.cartfrenzy.util.CategoryConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto getCategoryById(Long id) {

        return categoryRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public CategoryDto getCategoryByName(String name) {

        return categoryRepository.findByName(name)
                .map(this::convertToDto).get();
    }

    @Override
    public List<CategoryDto> getAllCategories() {

        return convertToDtoList(categoryRepository.findAll());
    }

    @Override
    public CategoryDto addCategory(Category category) {

        return Optional.of(category)
                .filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .map(this::convertToDto)
                .orElseThrow(() -> new AlreadyExistsException(category.getName() + " already exists"));

    }

    @Override
    public CategoryDto updateCategory(Category category, Long id) {

        return Optional.ofNullable(getCategoryById(id))
                .map(this::convertToEntity).map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return convertToDto(categoryRepository.save(oldCategory));
                }).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

    }

    @Override
    public void deleteCategoryById(Long id) {

        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, () -> {
                    throw new ResourceNotFoundException("Category not found!");
                });
    }

    private CategoryDto convertToDto(Category category) {
        return CategoryConverter.convertToDto(category);
    }

    private Category convertToEntity(CategoryDto categoryDto) {
        return CategoryConverter.convertToEntity(categoryDto);
    }

    private List<CategoryDto> convertToDtoList(List<Category> categoryList) {
        return categoryList.stream()
                .map(CategoryConverter::convertToDto)
                .collect(Collectors.toList());
    }

//	private List<Category> convertToEntityList(List<CategoryDto> categoryDtoList) {
//		return categoryDtoList.stream()
//				.map(CategoryConverter::convertToEntity)
//				.collect(Collectors.toList());
//	}


}
