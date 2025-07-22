package com.example.crave.clock.portal.serviceImpl;

import com.example.crave.clock.portal.dto.CartItemDTO;
import com.example.crave.clock.portal.entity.CartItemEntity;
import com.example.crave.clock.portal.repository.CartItemRepository;
import com.example.crave.clock.portal.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    @Override
    public CartItemDTO addToCart(CartItemDTO dto) {
        log.info("Adding item to cart: userId={}, vendorId={}, menuId={}, quantity={}", dto.getUserId(),
                dto.getVendorId(), dto.getMenuId(), dto.getQuantity());
        CartItemEntity item = new CartItemEntity();
        item.setUserId(dto.getUserId());
        item.setVendorId(dto.getVendorId());
        item.setMenuId(dto.getMenuId());
        item.setQuantity(dto.getQuantity());
        item.setPrice(dto.getPrice());
        item.setTotalPrice(dto.getPrice().multiply(
                new java.math.BigDecimal(dto.getQuantity())));
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        item.setCreatedAt(now);
        item.setUpdatedAt(now);

        CartItemEntity saved = cartItemRepository.save(item);
        log.info("Cart item added with cartItemId={}", saved.getCartItemId());
        dto.setCartItemId(saved.getCartItemId());
        dto.setTotalPrice(saved.getTotalPrice());
        dto.setCreatedAt(saved.getCreatedAt());
        dto.setUpdatedAt(saved.getUpdatedAt());
        return dto;
    }

    @Override
    public List<CartItemDTO> getCartByUser(Long userId) {
        log.info("Fetching cart for userId={}", userId);
        List<CartItemDTO> cart = cartItemRepository.findByUserId(userId).stream()
                .map(item -> {
                    CartItemDTO dto = new CartItemDTO();
                    dto.setCartItemId(item.getCartItemId());
                    dto.setUserId(item.getUserId());
                    dto.setVendorId(item.getVendorId());
                    dto.setMenuId(item.getMenuId());
                    dto.setQuantity(item.getQuantity());
                    dto.setPrice(item.getPrice());
                    dto.setTotalPrice(item.getTotalPrice());
                    dto.setCreatedAt(item.getCreatedAt());
                    dto.setUpdatedAt(item.getUpdatedAt());
                    return dto;
                }).collect(Collectors.toList());
        log.info("Fetched {} cart items for userId={}", cart.size(), userId);
        return cart;
    }

    @Override
    public void removeFromCart(Long userId, Long cartItemId) {
        cartItemRepository.findById(cartItemId).ifPresent(cartItem -> {
            if (cartItem.getUserId().equals(userId)) {
                cartItemRepository.deleteById(cartItemId);
            }
        });
    }

    @Override
    public CartItemDTO updateCartItem(Long userId, Long cartItemId, Integer quantity) {
        log.info("Updating cart item: userId={}, cartItemId={}, newQuantity={}", userId, cartItemId, quantity);
        return cartItemRepository.findById(cartItemId)
                .filter(item -> item.getUserId().equals(userId))
                .map(item -> {
                    item.setQuantity(quantity);
                    item.setTotalPrice(item.getPrice().multiply(new java.math.BigDecimal(quantity)));
                    item.setUpdatedAt(java.time.LocalDateTime.now());
                    CartItemEntity saved = cartItemRepository.save(item);
                    log.info("Cart item updated: cartItemId={}, newQuantity={}", saved.getCartItemId(),
                            saved.getQuantity());
                    CartItemDTO dto = new CartItemDTO();
                    dto.setCartItemId(saved.getCartItemId());
                    dto.setUserId(saved.getUserId());
                    dto.setVendorId(saved.getVendorId());
                    dto.setMenuId(saved.getMenuId());
                    dto.setQuantity(saved.getQuantity());
                    dto.setPrice(saved.getPrice());
                    dto.setTotalPrice(saved.getTotalPrice());
                    dto.setCreatedAt(saved.getCreatedAt());
                    dto.setUpdatedAt(saved.getUpdatedAt());
                    return dto;
                })
                .orElseGet(() -> {
                    log.warn("Cart item not found or userId mismatch: userId={}, cartItemId={}", userId, cartItemId);
                    return null;
                });
    }

    @Override
    public void clearCart(Long userId) {
        log.info("Clearing cart for userId={}", userId);
        List<CartItemEntity> items = cartItemRepository.findByUserId(userId);
        cartItemRepository.deleteAll(items);
        log.info("Cleared {} cart items for userId={}", items.size(), userId);
    }

    @Override
    public List<CartItemDTO> syncCart(Long userId, List<CartItemDTO> items) {
        log.info("Syncing cart for userId={}, itemsCount={}", userId, items.size());
        // Remove all existing items for user
        List<CartItemEntity> existing = cartItemRepository.findByUserId(userId);
        cartItemRepository.deleteAll(existing);
        log.info("Deleted {} existing cart items for userId={}", existing.size(), userId);
        // Add new items
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        List<CartItemEntity> toSave = items.stream().map(dto -> {
            CartItemEntity entity = new CartItemEntity();
            entity.setUserId(userId);
            entity.setVendorId(dto.getVendorId());
            entity.setMenuId(dto.getMenuId());
            entity.setQuantity(dto.getQuantity());
            entity.setPrice(dto.getPrice());
            entity.setTotalPrice(dto.getPrice().multiply(new java.math.BigDecimal(dto.getQuantity())));
            entity.setCreatedAt(now);
            entity.setUpdatedAt(now);
            return entity;
        }).collect(Collectors.toList());
        List<CartItemEntity> saved = cartItemRepository.saveAll(toSave);
        log.info("Saved {} new cart items for userId={}", saved.size(), userId);
        return saved.stream().map(savedItem -> {
            CartItemDTO dto = new CartItemDTO();
            dto.setCartItemId(savedItem.getCartItemId());
            dto.setUserId(savedItem.getUserId());
            dto.setVendorId(savedItem.getVendorId());
            dto.setMenuId(savedItem.getMenuId());
            dto.setQuantity(savedItem.getQuantity());
            dto.setPrice(savedItem.getPrice());
            dto.setTotalPrice(savedItem.getTotalPrice());
            dto.setCreatedAt(savedItem.getCreatedAt());
            dto.setUpdatedAt(savedItem.getUpdatedAt());
            return dto;
        }).collect(Collectors.toList());
    }
}
