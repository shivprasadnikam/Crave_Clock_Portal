package com.example.crave.clock.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "CC_VENDORS")
@Data
public class VendorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VENDOR_ID")
    private Long vendorId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "WEBSITE")
    private String website;

    @Column(name = "ADDRESS_LINE1")
    private String addressLine1;

    @Column(name = "ADDRESS_LINE2")
    private String addressLine2;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "ZIP_CODE", length = 20)
    private String zipCode;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "OPEN_STATUS")
    private String openStatus;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;



}
