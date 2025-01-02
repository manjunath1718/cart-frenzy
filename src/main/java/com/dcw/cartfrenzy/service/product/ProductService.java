package com.dcw.cartfrenzy.service.product;

import com.dcw.cartfrenzy.dto.ProductDto;
import com.dcw.cartfrenzy.exception.AlreadyExistsException;
import com.dcw.cartfrenzy.exception.ProductNotFoundException;
import com.dcw.cartfrenzy.exception.ResourceNotFoundException;
import com.dcw.cartfrenzy.model.Category;
import com.dcw.cartfrenzy.model.Product;
import com.dcw.cartfrenzy.repository.CategoryRepository;
import com.dcw.cartfrenzy.repository.ProductRepository;
import com.dcw.cartfrenzy.request.AddProductRequest;
import com.dcw.cartfrenzy.request.ProductUpdateRequest;
import com.dcw.cartfrenzy.service.user.IUserService;
import com.dcw.cartfrenzy.util.OwnershipUtil;
import com.dcw.cartfrenzy.util.ProductConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final IUserService userService;
    private final OwnershipUtil ownershipUtil;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, IUserService userService, OwnershipUtil ownershipUtil) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.ownershipUtil = ownershipUtil;
    }

    @Override
    public ProductDto addProduct(AddProductRequest request) {
        // check if the product already exists in the DB
        // If Yes, throw an exception
        // If No, continue with the process
        // Check if the category is found in the DB
        // If Yes, set it as the new product category
        // If No, the save it as a new category
        // The set as the new product category.
        // Save the new product in the DB

        if (productExists(request.getName(), request.getBrand())) {

            throw new AlreadyExistsException("product already exists!");
        }

        Category category = categoryRepository.findByName(request.getCategory())
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category.getName());

        return convertToProductDto(productRepository.save(createProduct(request, category)));
    }

    private Product createProduct(AddProductRequest request, Category category) {

        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                userService.getConsumerId(),
                category);
    }

    @Override
    public boolean productExists(String name, String brand) {

        return productRepository.existsByNameAndBrand(name, brand);
    }

    @Override
    public ProductDto getProductById(Long id) {

        return productRepository.findById(id).map(this::convertToProductDto)
                .orElseThrow(() -> new ProductNotFoundException("Product  not found!"));
    }

    @Override
    public void deleteProductById(Long id) {

        productRepository.findById(id)
                .map(product -> {
                    checkOwnership(product.getId());
                    return product;
                })
                .ifPresentOrElse(productRepository::delete, () -> {
                    throw new ProductNotFoundException("Product  not found!");
                });
    }

    @Override
    public ProductDto updateProductById(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(product -> {
                    checkOwnership(product.getId());
                    Product updatedProduct = updateExistingProduct(product, request);
                    return convertToProductDto(productRepository.save(updatedProduct));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName() != null ? request.getName() : existingProduct.getName());
        existingProduct.setBrand(request.getBrand() != null ? request.getBrand() : existingProduct.getBrand());
        existingProduct.setPrice(request.getPrice() != null ? request.getPrice() : existingProduct.getPrice());
        existingProduct.setInventory(request.getInventory() != 0 ? request.getInventory() : existingProduct.getInventory());
        existingProduct.setDescription(request.getDescription() != null ? request.getDescription() : existingProduct.getDescription());
        existingProduct.setCategory(request.getCategory() != null ? categoryRepository.findByName(request.getCategory()).orElse(new Category(request.getCategory())) : existingProduct.getCategory());

        return existingProduct;
    }

    @Override
    public List<ProductDto> getAllProducts() {

        return productRepository.findAll().stream()
                .map(this::convertToProductDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByCategoryName(String category) {

        return productRepository.findByCategoryName(category).stream()
                .map(this::convertToProductDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {

        return productRepository.findByBrand(brand).stream()
                .map(this::convertToProductDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByCategoryNameAndBrand(String category, String brand) {

        return productRepository.findByCategoryNameAndBrand(category, brand).stream()
                .map(this::convertToProductDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByName(String name) {

        return productRepository.findByName(name).stream()
                .map(this::convertToProductDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByBrandAndName(String brand, String name) {

        return productRepository.findByBrandAndName(brand, name).stream()
                .map(this::convertToProductDto)
                .toList();
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {

        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {

        return products.stream().map(this::convertToProductDto).toList();
    }

    @Override
    public ProductDto convertToProductDto(Product product) {

        //		ProductDto productDto = mapper.map(product, ProductDto.class);
        //
        //		List<Image> images = imageRepository.findByProductId(product.getId());
        //		List<ImageDto> imageDtos = images.stream()
        //				.map(image -> mapper.map(image, ImageDto.class))
        //				.toList();
        //
        //		Category category = categoryRepository.findByName(product.getCategory().getName()).get();
        //		productDto.setCategory(mapper.map(category, CategoryDto.class));
        //		productDto.setImages(imageDtos);

        return ProductConverter.convertToDto(product);
    }

    private void checkOwnership(Long productId) {

        ownershipUtil.checkOwnership(productId);
    }

}
