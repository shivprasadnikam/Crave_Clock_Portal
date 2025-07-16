package com.example.crave.clock.portal.serviceImpl;

import com.example.crave.clock.portal.dto.CartItemDTO;
import com.example.crave.clock.portal.entity.CartItemEntity;
import com.example.crave.clock.portal.repository.CartItemRepository;
import com.example.crave.clock.portal.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;



    @Override
    public CartItemDTO addToCart(CartItemDTO dto) {
        CartItemEntity item = new CartItemEntity();
        item.setUserId(dto.getUserId());
        item.setVendorId(dto.getVendorId());
        item.setMenuId(dto.getMenuId());
        item.setQuantity(dto.getQuantity());
        item.setPrice(dto.getPrice());

        item.setTotalPrice(dto.getPrice().multiply(
                new java.math.BigDecimal(dto.getQuantity())
        ));

        CartItemEntity saved = cartItemRepository.save(item);
        dto.setCartItemId(saved.getCartItemId());
        dto.setTotalPrice(saved.getTotalPrice());
        return dto;
    }

    @Override
    public List<CartItemDTO> getCartByUser(Long userId) {
        return cartItemRepository.findByUserId(userId).stream()
                .map(item -> {
                    CartItemDTO dto = new CartItemDTO();
                    dto.setCartItemId(item.getCartItemId());
                    dto.setUserId(item.getUserId());
                    dto.setVendorId(item.getVendorId());
                    dto.setMenuId(item.getMenuId());
                    dto.setQuantity(item.getQuantity());
                    dto.setPrice(item.getPrice());
                    dto.setTotalPrice(item.getTotalPrice());
                    return dto;
                }).collect(Collectors.toList());
    }
    @Override
    public void removeFromCart(Long userId, Long cartItemId) {
        cartItemRepository.findById(cartItemId).ifPresent(cartItem -> {
            if (cartItem.getUserId().equals(userId)) {
                cartItemRepository.deleteById(cartItemId);
            }
        });
    }
}
