package com.example.crave.clock.portal.controller;

import com.example.crave.clock.portal.dto.CartItemDTO;
import com.example.crave.clock.portal.service.CartItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping("/add")
    public ResponseEntity<CartItemDTO> addToCart(@RequestBody CartItemDTO dto) {
        log.info("[CartItemController] addToCart called with userId={}, vendorId={}, menuId={}, quantity={}",
                dto.getUserId(), dto.getVendorId(), dto.getMenuId(), dto.getQuantity());
        CartItemDTO result = cartItemService.addToCart(dto);
        log.info("[CartItemController] addToCart result: cartItemId={}",
                result != null ? result.getCartItemId() : null);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemDTO>> getCart(@PathVariable Long userId) {
        log.info("[CartItemController] getCart called for userId={}", userId);
        List<CartItemDTO> cart = cartItemService.getCartByUser(userId);
        log.info("[CartItemController] getCart result: {} items", cart.size());
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{userId}/{cartItemId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long userId, @PathVariable Long cartItemId) {
        log.info("[CartItemController] removeFromCart called for userId={}, cartItemId={}", userId, cartItemId);
        cartItemService.removeFromCart(userId, cartItemId);
        log.info("[CartItemController] removeFromCart completed for cartItemId={}", cartItemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/{cartItemId}")
    public ResponseEntity<CartItemDTO> updateCartItem(@PathVariable Long userId, @PathVariable Long cartItemId,
            @RequestBody CartItemDTO dto) {
        log.info("[CartItemController] updateCartItem called for userId={}, cartItemId={}, newQuantity={}", userId,
                cartItemId, dto.getQuantity());
        CartItemDTO result = cartItemService.updateCartItem(userId, cartItemId, dto.getQuantity());
        log.info("[CartItemController] updateCartItem result: {}", result != null ? "success" : "not found");
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        log.info("[CartItemController] clearCart called for userId={}", userId);
        cartItemService.clearCart(userId);
        log.info("[CartItemController] clearCart completed for userId={}", userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/sync/{userId}")
    public ResponseEntity<List<CartItemDTO>> syncCart(@PathVariable Long userId, @RequestBody List<CartItemDTO> items) {
        log.info("[CartItemController] syncCart called for userId={}, itemsCount={}", userId, items.size());
        List<CartItemDTO> result = cartItemService.syncCart(userId, items);
        log.info("[CartItemController] syncCart completed for userId={}, newItemsCount={}", userId, result.size());
        return ResponseEntity.ok(result);
    }
}
