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
        log.info("Add to cart called");
        return ResponseEntity.ok(cartItemService.addToCart(dto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemDTO>> getCart(@PathVariable Long userId) {
        log.info("getCart called");
        return ResponseEntity.ok(cartItemService.getCartByUser(userId));
    }

    @DeleteMapping("/{userId}/{cartItemId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long userId, @PathVariable Long cartItemId) {
        cartItemService.removeFromCart(userId, cartItemId);
        return ResponseEntity.noContent().build();
    }
}
