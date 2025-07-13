package com.example.crave.clock.portal.controller;

import com.example.crave.clock.portal.dto.VendorDTO;
import com.example.crave.clock.portal.service.VendorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class RestaurantDetailScreenController {

    private final VendorService vendorService;


    @GetMapping("api/restaurants")
    public List<VendorDTO> getAllVendors() {
        log.info("Vendor controller called");
        return vendorService.getAllVendors();
    }
}
