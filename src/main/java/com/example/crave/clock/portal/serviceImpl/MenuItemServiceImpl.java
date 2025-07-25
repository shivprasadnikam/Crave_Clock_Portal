package com.example.crave.clock.portal.serviceImpl;


import com.example.crave.clock.portal.dto.MenuItemDTO;
import com.example.crave.clock.portal.entity.MenuItemEntity;
import com.example.crave.clock.portal.repository.MenuItemRepository;
import com.example.crave.clock.portal.service.MenuItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public List<MenuItemDTO> getMenuByVendor(Long vendorId) {
        List<MenuItemEntity> items = menuItemRepository.findByVendorId(vendorId);
        return items.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private MenuItemDTO toDTO(MenuItemEntity menuItem) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setMenuId(menuItem.getMenuId());
        dto.setVendorId(menuItem.getVendorId());
        dto.setItemName(menuItem.getItemName());
        dto.setDescription(menuItem.getDescription());
        dto.setPrice(menuItem.getPrice());
        dto.setCategory(menuItem.getCategory());
        dto.setIsAvailable(menuItem.getIsAvailable());
        return dto;
    }
}
