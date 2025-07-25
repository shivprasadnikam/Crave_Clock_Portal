package com.example.crave.clock.portal.service;

import com.example.crave.clock.portal.dto.UserProfileDTO;

public interface ProfileService {
    UserProfileDTO getUserProfileById(String userId);

    UserProfileDTO updateUserProfile(String userId, UserProfileDTO profileDTO);

    boolean updateExpoPushToken(String userId, String expoPushToken);
}