package com.dcw.cartfrenzy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dcw.cartfrenzy.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findCartByUserId(Long userId);

	

}
