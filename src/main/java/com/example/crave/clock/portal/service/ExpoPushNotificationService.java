package com.example.crave.clock.portal.service;

import java.util.Map;

public interface ExpoPushNotificationService {
    boolean sendPushNotification(String expoPushToken, String title, String body, Map<String, Object> data);
}