package com.dcw.cartfrenzy.service.product;

import java.util.List;

import com.dcw.cartfrenzy.dto.ProductDto;
import com.dcw.cartfrenzy.model.Product;
import com.dcw.cartfrenzy.request.AddProductRequest;
import com.dcw.cartfrenzy.request.ProductUpdateRequest;

public interface IProductService {

	ProductDto addProduct(AddProductRequest product);
	ProductDto getProductById(Long id);
	void deleteProductById(Long id);
	ProductDto updateProductById(ProductUpdateRequest product,Long productId);
	List<ProductDto> getAllProducts();
	List<ProductDto> getProductsByCategoryName(String category);
	List<ProductDto> getProductsByBrand(String brand);
	List<ProductDto> getProductsByCategoryNameAndBrand(String category,String brand);
	List<ProductDto> getProductsByName(String name);
	List<ProductDto> getProductsByBrandAndName(String brand,String name);
	Long countProductsByBrandAndName(String brand,String name);
	ProductDto convertToProductDto(Product product);
	List<ProductDto> getConvertedProducts(List<Product> products);
	boolean productExists(String name, String brand);
}
