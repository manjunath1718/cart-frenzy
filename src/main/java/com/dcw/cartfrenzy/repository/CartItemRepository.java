package com.dcw.cartfrenzy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dcw.cartfrenzy.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	void deleteAllByCartId(Long id);

}
