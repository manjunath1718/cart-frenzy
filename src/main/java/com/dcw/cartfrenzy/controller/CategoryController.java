package com.dcw.cartfrenzy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcw.cartfrenzy.exception.AlreadyExistsException;
import com.dcw.cartfrenzy.exception.ResourceNotFoundException;
import com.dcw.cartfrenzy.model.Category;
import com.dcw.cartfrenzy.response.ApiResponse;
import com.dcw.cartfrenzy.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

	private final ICategoryService categoryService;
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllCategory(){
		
		try {
			
			List<Category> categories = categoryService.getAllCategories();			
			return ResponseEntity.ok(new ApiResponse("Found!", categories));			
		}catch(Exception e){
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error",INTERNAL_SERVER_ERROR));
		}		
	}
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category){
		
		try {
			Category theCategory= categoryService.addCategory(category);
			return ResponseEntity.ok(new ApiResponse("Success", theCategory));
		}catch(AlreadyExistsException e) {
			return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}		
	}
	
	@GetMapping("/category/{id}/category")
	public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
		
		try {
			Category theCategory= categoryService.getCategoryById(id);
			return ResponseEntity.ok(new ApiResponse("Found!", theCategory));
		}catch(ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}		
	}
	
	@GetMapping("/category/{name}/category")
	public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
		
		try {
			Category theCategory= categoryService.getCategoryByName(name);
			return ResponseEntity.ok(new ApiResponse("Found!", theCategory));
		}catch(ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}		
	}
	
	@DeleteMapping("/category/{id}/delete")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
		
		try {
			categoryService.deleteCategoryById(id);
			return ResponseEntity.ok(new ApiResponse("Delete success!", null));
		}catch(ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}		
	}
	
	@PutMapping("/category/{id}/update")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,@RequestBody Category category){
		
		try {
			Category theCategory = categoryService.updateCategory(category,id);
			return ResponseEntity.ok(new ApiResponse("Update success!", theCategory));
		}catch(ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}		
	}
	
	
	
	
}
