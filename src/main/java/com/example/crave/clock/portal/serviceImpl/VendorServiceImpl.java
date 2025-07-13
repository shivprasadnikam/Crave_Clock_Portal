package com.example.crave.clock.portal.serviceImpl;

import com.example.crave.clock.portal.dto.VendorDTO;
import com.example.crave.clock.portal.entity.VendorEntity;
import com.example.crave.clock.portal.repository.VendorRepository;
import com.example.crave.clock.portal.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {
    @Autowired
    private  VendorRepository vendorRepository;


    @Override
    public List<VendorDTO> getAllVendors() {
        List<VendorEntity> vendors = vendorRepository.findAll();
        return vendors.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private VendorDTO convertToDto(VendorEntity vendor) {
        VendorDTO dto = new VendorDTO();
        dto.setVendorId(vendor.getVendorId());
        dto.setName(vendor.getName());
        dto.setPhoneNumber(vendor.getPhoneNumber());
        dto.setCity(vendor.getCity());
        dto.setState(vendor.getState());
        dto.setCountry(vendor.getCountry());
        dto.setOpenStatus(vendor.getOpenStatus());
        return dto;
    }
}
