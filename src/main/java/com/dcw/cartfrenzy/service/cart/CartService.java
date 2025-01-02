package com.dcw.cartfrenzy.service.cart;

import com.dcw.cartfrenzy.dto.CartDto;
import com.dcw.cartfrenzy.exception.ResourceNotFoundException;
import com.dcw.cartfrenzy.model.Cart;
import com.dcw.cartfrenzy.model.User;
import com.dcw.cartfrenzy.repository.CartRepository;
import com.dcw.cartfrenzy.service.user.IUserService;
import com.dcw.cartfrenzy.util.CartConverter;
import com.dcw.cartfrenzy.util.UserConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final IUserService userService;

    public CartService(CartRepository cartRepository, IUserService userService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    @Override
    public Cart getCart(Long id) {

        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
    }

    @Override
    public CartDto getCartDto(Long cartId) {

        Cart cart = getCart(cartId);
        return CartConverter.convertToDto(cart);
    }

    @Transactional
    @Override
    public void clearCart() {

        Cart cart = getCartByUserId();
        cart.clearCart();
        cartRepository.deleteById(cart.getId());
    }

    @Override
    public BigDecimal getTotalPrice() {

        return getCartByUserId().getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(User user) {

        return Optional.ofNullable(cartRepository.findCartByUserId(user.getId()))
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);

                    return cartRepository.save(cart);
                });

    }

    @Override
    public CartDto getCart() {

        return CartConverter.convertToDto(getCartByUserId());
    }

    @Override
    public Cart getCartByUserId() {

        return initializeNewCart(
                UserConverter.convertToEntity(
                        userService.getUserById(
                                userService.getConsumerId()
                        )
                )
        );

    }


}
