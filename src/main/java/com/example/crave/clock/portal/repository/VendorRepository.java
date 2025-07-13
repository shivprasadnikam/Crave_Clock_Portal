package com.example.crave.clock.portal.repository;

import com.example.crave.clock.portal.entity.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, Long> {

    List<VendorEntity> findByCity(String city);

    List<VendorEntity> findByOpenStatus(String status);

    List<VendorEntity> findByNameContainingIgnoreCase(String namePart);

}
