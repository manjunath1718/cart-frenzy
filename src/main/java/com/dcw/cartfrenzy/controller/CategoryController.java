package com.dcw.cartfrenzy.controller;

import com.dcw.cartfrenzy.dto.CategoryDto;
import com.dcw.cartfrenzy.model.Category;
import com.dcw.cartfrenzy.response.ApiResponse;
import com.dcw.cartfrenzy.service.category.ICategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllCategory() {

        List<CategoryDto> categories = categoryService.getAllCategories();

        return ResponseEntity.ok(new ApiResponse("Found!", categories));
    }

    @PostMapping("/category")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {

        CategoryDto theCategory = categoryService.addCategory(category);

        return ResponseEntity.ok(new ApiResponse("Success", theCategory));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {

        CategoryDto theCategory = categoryService.getCategoryById(id);

        return ResponseEntity.ok(new ApiResponse("Found!", theCategory));
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {

        CategoryDto theCategory = categoryService.getCategoryByName(name);

        return ResponseEntity.ok(new ApiResponse("Found!", theCategory));
    }

//	@DeleteMapping("/category/{id}")
//	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
//
//		categoryService.deleteCategoryById(id);
//
//		return ResponseEntity.ok(new ApiResponse("Delete success!", null));				
//	}

    @PutMapping("/category/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {

        CategoryDto theCategory = categoryService.updateCategory(category, id);

        return ResponseEntity.ok(new ApiResponse("Update success!", theCategory));
    }


}
