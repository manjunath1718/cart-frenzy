package com.dcw.cartfrenzy.service.cart;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.dcw.cartfrenzy.exception.ResourceNotFoundException;
import com.dcw.cartfrenzy.model.Cart;
import com.dcw.cartfrenzy.model.CartItem;
import com.dcw.cartfrenzy.model.Product;
import com.dcw.cartfrenzy.repository.CartItemRepository;
import com.dcw.cartfrenzy.repository.CartRepository;
import com.dcw.cartfrenzy.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

	private final CartItemRepository cartItemRepository;
	private final CartRepository cartRepository;
	private final ICartService cartService;
	private final IProductService productService;
	
	
	@Override
	public void addItemToCart(Long cartId, Long productId, int quantity) {		
		//1. Get the cart
        //2. Get the product
        //3. Check if the product already in the cart
        //4. If Yes, then increase the quantity with the requested quantity
        //5. If No, then initiate a new CartItem entry.
		
		Cart cart = cartService.getCart(cartId);
		System.out.println("cart id "+cartId);//
	
		System.out.println("cart id db "+cart.getId());//
		Product product = productService.getProductById(productId);
        System.out.println(product.getName());//
		CartItem cartItem = cart.getItems()
				.stream()
				.filter(item->item.getProduct().getId().equals(productId))
				.findFirst().orElse(new CartItem());

		if(cartItem.getId() == null ) {
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
			cartItem.setUnitPrice(product.getPrice());
		}else {
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		}
		cartItem.setTotalPrice();
		cart.addItem(cartItem);
		cartItemRepository.save(cartItem);
		cartRepository.save(cart);
		
	}

	@Override
	public void removeItemFromCart(Long cartId, Long productId) {
		
		Cart cart = cartService.getCart(cartId);
		CartItem itemToRemove = getCartItem(cartId,productId);
		cart.removeItem(itemToRemove);
		
		cartRepository.save(cart);
	}

	@Override
	public void updateItemQuantity(Long cartId, Long productId, int quantity) {
		
		Cart cart = cartService.getCart(cartId);
		cart.getItems()
		.stream()
		.filter(item -> item.getProduct().getId().equals(productId))
		.findFirst()
		.ifPresent(item -> {
			item.setQuantity(quantity);
			item.setUnitPrice(item.getProduct().getPrice());
			item.setTotalPrice();
		});
		BigDecimal totalPrice = cart.getItems().stream()
				.map(CartItem::getTotalPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		cart.setTotalAmount(totalPrice);
		
		cartRepository.save(cart);
	}

	@Override
	public CartItem getCartItem(Long cartId, Long productId) {
		Cart cart = cartService.getCart(cartId);
		return cart.getItems()
				.stream()
				.filter(item->item.getProduct().getId().equals(productId))
				.findFirst().orElseThrow(()->new ResourceNotFoundException("Cart-Item not found"));
	}

}
