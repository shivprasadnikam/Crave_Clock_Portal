package com.example.crave.clock.portal.serviceImpl;

import com.example.crave.clock.portal.service.ExpoPushNotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Service
public class ExpoPushNotificationServiceImpl implements ExpoPushNotificationService {
    private static final String EXPO_API_URL = "https://exp.host/--/api/v2/push/send";

    @Override
    public boolean sendPushNotification(String expoPushToken, String title, String body, Map<String, Object> data) {
        try {
            URL url = new URL(EXPO_API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> message = new java.util.HashMap<>();
            message.put("to", expoPushToken);
            message.put("title", title);
            message.put("body", body);
            if (data != null) {
                message.put("data", data);
            }
            String jsonMessage = objectMapper.writeValueAsString(message);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonMessage.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            return responseCode == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}