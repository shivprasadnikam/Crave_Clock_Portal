package com.example.crave.clock.portal.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderUtility {
    public static String generateOrderId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return "ORD_" + LocalDateTime.now().format(formatter);
    }
}
