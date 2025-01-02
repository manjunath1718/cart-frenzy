package com.dcw.cartfrenzy.util;

import com.dcw.cartfrenzy.exception.ResourceNotFoundException;
import com.dcw.cartfrenzy.model.Product;
import com.dcw.cartfrenzy.repository.ProductRepository;
import com.dcw.cartfrenzy.security.user.ShopUserDetails;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class OwnershipUtil {

    private final ProductRepository productRepository;

    public OwnershipUtil(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static Long getConsumerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((ShopUserDetails) authentication.getPrincipal()).getId();
    }

    public void checkOwnership(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (product.getAdminId() == null || !product.getAdminId().equals(getConsumerId())) {
            throw new AccessDeniedException("You do not have permission to modify this product");
        }
    }

}
