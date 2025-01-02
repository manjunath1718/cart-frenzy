package com.dcw.cartfrenzy.service.cart;

import com.dcw.cartfrenzy.model.CartItem;

public interface ICartItemService {

	void addItemToCart(Long productId,Integer quantity);
	void removeItemFromCart(Long itemId);
	void updateItemQuantity(Long itemId,Integer quantity);
	
	CartItem getCartItem(Long cartId,Long itemId);
}
