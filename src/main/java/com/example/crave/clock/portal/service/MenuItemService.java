package com.example.crave.clock.portal.service;

import com.example.crave.clock.portal.dto.MenuItemDTO;

import java.util.List;

public interface MenuItemService {
    List<MenuItemDTO> getMenuByVendor(Long vendorId);
}
