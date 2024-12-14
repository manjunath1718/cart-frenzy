package com.dcw.cartfrenzy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcw.cartfrenzy.exception.ResourceNotFoundException;
import com.dcw.cartfrenzy.model.Product;
import com.dcw.cartfrenzy.request.AddProductRequest;
import com.dcw.cartfrenzy.request.ProductUpdateRequest;
import com.dcw.cartfrenzy.response.ApiResponse;
import com.dcw.cartfrenzy.service.product.IProductService;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {

	private final IProductService productService;
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllProducts(){

		List<Product> products = productService.getAllProducts();
		if(products == null) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Failed", null));
		}
		return ResponseEntity.ok(new ApiResponse("success", products));
	}
	
	@GetMapping("product/{productId}/product")
	public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){

		try {
			Product product = productService.getProductById(productId);
			return ResponseEntity.ok(new ApiResponse("success", product));
			
		}catch(ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest request){

		try {
			Product product = productService.addProduct(request);
			return ResponseEntity.ok(new ApiResponse("success", product));
			
		}catch(Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	@PostMapping("product/{productId}/update")
	public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request,@PathVariable Long productId){

		try {
			Product product = productService.updateProductById(request,productId);
			return ResponseEntity.ok(new ApiResponse("success", product));
			
		}catch(ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	@DeleteMapping("product/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProductById(@PathVariable Long productId){

		try {
			productService.deleteProductById(productId);
			return ResponseEntity.ok(new ApiResponse("Delete product success!", productId));
			
		}catch(ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	@GetMapping("/product/by/brand-and-name")
	public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName,@RequestParam String productName){

		try {
			
			List<Product> products = productService.getProductsByBrandAndName(brandName,productName);
			if(products.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse("No products found", null));
			}
			return ResponseEntity.ok(new ApiResponse("success", products));
		}catch(Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null)); 
		}
		
	}
	
	@GetMapping("/product/by/category-and-brand")
	public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category,@RequestParam String brandName){

		try {
			
			List<Product> products = productService.getProductsByCategoryNameAndBrand(category,brandName);
			if(products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
			}
			return ResponseEntity.ok(new ApiResponse("success", products));
		}catch(Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null)); 
		}
		
	}
	
	@GetMapping("/product/{name}/product")
	public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){

		try {
			
			List<Product> products = productService.getProductsByName(name);
			if(products.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse("No products found", null));
			}
			return ResponseEntity.ok(new ApiResponse("success", products));
		}catch(Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null)); 
		}
		
	}
	
	@GetMapping("/product/by-brand")
	public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand){

		try {
			
			List<Product> products = productService.getProductsByBrand(brand);
			if(products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
			}
			return ResponseEntity.ok(new ApiResponse("success", products));
		}catch(Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null)); 
		}
		
	}
	
	@GetMapping("/product/{category}/all/products")
	public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category){

		try {
			
			List<Product> products = productService.getProductsByCategoryName(category);
			if(products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
			}
			return ResponseEntity.ok(new ApiResponse("success", products));
		}catch(Exception e) {
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null)); 
		}
		
	}
	
	@GetMapping("/product/count/by-brand/and-name")
	public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand,@RequestParam String name){

		try {
			var productCount = productService.countProductsByBrandAndName(brand, name);
			return ResponseEntity.ok(new ApiResponse("product count!", productCount));
			
		}catch(ResourceNotFoundException e) {
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	
	
}
