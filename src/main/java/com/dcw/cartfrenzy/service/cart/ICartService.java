package com.dcw.cartfrenzy.service.cart;

import java.math.BigDecimal;

import com.dcw.cartfrenzy.model.Cart;

public interface ICartService {

	Cart getCart(Long id);
	void clearCart(Long id);
	BigDecimal getTotalPrice(Long id);
	
	Long initializeNewCart();
	
}
