package com.example.crave.clock.portal.repository;

import com.example.crave.clock.portal.entity.AppProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppPropertyRepository extends JpaRepository<AppProperty, String> {
    AppProperty findByPropertyKey(String propertyKey);

    List<AppProperty> findByServiceName(String serviceName);
}