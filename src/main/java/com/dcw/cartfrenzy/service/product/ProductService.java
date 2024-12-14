package com.dcw.cartfrenzy.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dcw.cartfrenzy.exception.ProductNotFoundException;
import com.dcw.cartfrenzy.exception.ResourceNotFoundException;
import com.dcw.cartfrenzy.model.Category;
import com.dcw.cartfrenzy.model.Product;
import com.dcw.cartfrenzy.repository.CategoryRepository;
import com.dcw.cartfrenzy.repository.ProductRepository;
import com.dcw.cartfrenzy.request.AddProductRequest;
import com.dcw.cartfrenzy.request.ProductUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
	
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	
	@Override
	public Product addProduct(AddProductRequest request) {
		
		// check if the category is found in the DB
        // If Yes, set it as the new product category
        // If No, the save it as a new category
        // The set as the new product category.
		Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
		.orElseGet(()->{
			Category newCategory = new Category(request.getCategory().getName());
			return categoryRepository.save(newCategory);
		});
		request.setCategory(category);
		
		return productRepository.save(createProduct(request,request.getCategory()));
	}
	private Product createProduct(AddProductRequest request,Category category) {
		
		return new Product(
				request.getName(),
				request.getBrand(),
				request.getPrice(),
				request.getInventory(),
				request.getDescription(),
				category);
	}

	@Override
	public Product getProductById(Long id) {
		
		return productRepository.findById(id)
				.orElseThrow(()-> new ProductNotFoundException("Product  not found!"));
	}

	@Override
	public void deleteProductById(Long id) {

		productRepository.findById(id)
		.ifPresentOrElse(productRepository::delete, () -> {
			throw new ProductNotFoundException("Product  not found!");
		});
	}

	@Override
	public Product updateProductById(ProductUpdateRequest request, Long productId) {
		
		 return productRepository.findById(productId)
				 .map(existingProduct -> updateExistingProduct(existingProduct,request))
				 .map(productRepository::save)
				 .orElseThrow(()-> new ResourceNotFoundException("Product not found!"));
		
	}
	private Product updateExistingProduct(Product existingProduct,ProductUpdateRequest request) {
		
		 existingProduct.setName(request.getName());
		 existingProduct.setBrand(request.getBrand());
		 existingProduct.setPrice(request.getPrice());
		 existingProduct.setInventory(request.getInventory());
		 existingProduct.setDescription(request.getDescription());
		 existingProduct.setCategory(categoryRepository.findByName(request.getCategory().getName()));
		return existingProduct;
	}

	@Override
	public List<Product> getAllProducts() {
		
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByCategoryName(String category) {
		
		return productRepository.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductsByCategoryNameAndBrand(String category, String brand) {
		
		return productRepository.findByCategoryNameAndBrand(category,brand);
	}

	@Override
	public List<Product> getProductsByName(String name) {
		
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductsByBrandAndName(String brand, String name) {
		
		return productRepository.findByBrandAndName(brand,name);
	}

	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		
		return productRepository.countByBrandAndName(brand,name);
	}

}
