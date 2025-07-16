package com.example.crave.clock.portal.service;

import com.example.crave.clock.portal.dto.CartItemDTO;

import java.util.List;

public interface CartItemService {
    CartItemDTO addToCart(CartItemDTO dto);
    List<CartItemDTO> getCartByUser(Long userId);
    void removeFromCart(Long userId, Long cartItemId);
}
