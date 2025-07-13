package com.example.crave.clock.portal.controller;

import com.example.crave.clock.portal.dto.MenuItemDTO;
import com.example.crave.clock.portal.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@Slf4j
public class MenuScreenController {

    private final MenuItemService menuItemService;



    @GetMapping("/{restaurantId}/menu")
    public List<MenuItemDTO> getMenuByRestaurant(@PathVariable String restaurantId) {
        log.info("Menu Controller called");
        return menuItemService.getMenuByVendor(Long.valueOf(restaurantId));
    }
}
