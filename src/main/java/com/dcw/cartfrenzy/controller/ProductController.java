package com.dcw.cartfrenzy.controller;


import com.dcw.cartfrenzy.dto.ProductDto;
import com.dcw.cartfrenzy.request.AddProductRequest;
import com.dcw.cartfrenzy.request.ProductUpdateRequest;
import com.dcw.cartfrenzy.response.ApiResponse;
import com.dcw.cartfrenzy.service.product.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {

        List<ProductDto> products = productService.getAllProducts();

        return ResponseEntity.ok(new ApiResponse("success", products));
    }

    @GetMapping("product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {

        ProductDto product = productService.getProductById(productId);

        return ResponseEntity.ok(new ApiResponse("success", product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest request) {

        ProductDto product = productService.addProduct(request);

        return ResponseEntity.status(201).body(new ApiResponse("success", product));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request,
                                                     @PathVariable Long productId) {

        ProductDto product = productService.updateProductById(request, productId);

        return ResponseEntity.ok(new ApiResponse("success", product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProductById(@PathVariable Long productId) {

        productService.deleteProductById(productId);

        return ResponseEntity.ok(new ApiResponse("Delete product success!", productId));
    }

    @GetMapping("/product/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName,
                                                                @RequestParam String productName) {

        List<ProductDto> products = productService.getProductsByBrandAndName(brandName, productName);

        if (products.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse("No products found", null));
        }

        return ResponseEntity.ok(new ApiResponse("success", products));

    }

    @GetMapping("/product/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category,
                                                                    @RequestParam String brandName) {

        List<ProductDto> products = productService.getProductsByCategoryNameAndBrand(category, brandName);

        if (products.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
        }

        return ResponseEntity.ok(new ApiResponse("success", products));

    }

    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {

        List<ProductDto> products = productService.getProductsByName(name);

        if (products.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse("No products found", null));
        }

        return ResponseEntity.ok(new ApiResponse("success", products));

    }

    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand) {

        List<ProductDto> products = productService.getProductsByBrand(brand);

        if (products.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
        }

        return ResponseEntity.ok(new ApiResponse("success", products));
    }

    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category) {

        List<ProductDto> products = productService.getProductsByCategoryName(category);

        if (products.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
        }

        return ResponseEntity.ok(new ApiResponse("success", products));

    }

    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand,
                                                                   @RequestParam String name) {

        var productCount = productService.countProductsByBrandAndName(brand, name);

        return ResponseEntity.ok(new ApiResponse("product count!", productCount));
    }

}
