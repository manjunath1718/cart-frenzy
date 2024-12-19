package com.dcw.cartfrenzy.service.product;

import java.util.List;

import com.dcw.cartfrenzy.dto.ProductDto;
import com.dcw.cartfrenzy.model.Product;
import com.dcw.cartfrenzy.request.AddProductRequest;
import com.dcw.cartfrenzy.request.ProductUpdateRequest;

public interface IProductService {

	Product addProduct(AddProductRequest product);
	Product getProductById(Long id);
	void deleteProductById(Long id);
	Product updateProductById(ProductUpdateRequest product,Long productId);
	List<Product> getAllProducts();
	List<Product> getProductsByCategoryName(String category);
	List<Product> getProductsByBrand(String brand);
	List<Product> getProductsByCategoryNameAndBrand(String category,String brand);
	List<Product> getProductsByName(String name);
	List<Product> getProductsByBrandAndName(String brand,String name);
	Long countProductsByBrandAndName(String brand,String name);
	ProductDto convertToProductDto(Product product);
	List<ProductDto> getConvertedProducts(List<Product> products);
}
