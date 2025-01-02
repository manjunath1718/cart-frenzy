package com.dcw.cartfrenzy.service.cart;

import java.math.BigDecimal;

import com.dcw.cartfrenzy.dto.CartDto;
import com.dcw.cartfrenzy.model.Cart;
import com.dcw.cartfrenzy.model.User;

public interface ICartService {

	Cart getCart(Long id);
	CartDto getCartDto(Long cartId);
	void clearCart();
	BigDecimal getTotalPrice();
	CartDto getCart();
	Cart getCartByUserId();
	Cart initializeNewCart(User user);
	
}
