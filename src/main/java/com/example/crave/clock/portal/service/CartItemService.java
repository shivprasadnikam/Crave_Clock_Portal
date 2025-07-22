package com.example.crave.clock.portal.service;

import com.example.crave.clock.portal.dto.CartItemDTO;

import java.util.List;

public interface CartItemService {
    CartItemDTO addToCart(CartItemDTO dto);

    List<CartItemDTO> getCartByUser(Long userId);

    void removeFromCart(Long userId, Long cartItemId);

    CartItemDTO updateCartItem(Long userId, Long cartItemId, Integer quantity);

    void clearCart(Long userId);

    List<CartItemDTO> syncCart(Long userId, List<CartItemDTO> items);
}
