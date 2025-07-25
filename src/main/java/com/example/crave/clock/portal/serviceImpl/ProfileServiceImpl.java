package com.example.crave.clock.portal.serviceImpl;

import com.example.crave.clock.portal.dto.UserProfileDTO;
import com.example.crave.clock.portal.entity.OnboardedUserEntity;
import com.example.crave.clock.portal.repository.RcUserDetailsRepository;
import com.example.crave.clock.portal.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private RcUserDetailsRepository rcUserDetailsRepository;

    @Override
    public UserProfileDTO getUserProfileById(String userId) {
        OnboardedUserEntity user = rcUserDetailsRepository.findById(Long.valueOf(userId)).orElse(null);
        if (user == null)
            return null;
        UserProfileDTO dto = new UserProfileDTO();
        dto.setUserId(user.getUserId());
        dto.setFullName(user.getFullName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());
        dto.setAddress(user.getAddress());
        return dto;
    }

    @Override
    public UserProfileDTO updateUserProfile(String userId, UserProfileDTO profileDTO) {
        OnboardedUserEntity user = rcUserDetailsRepository.findById(Long.valueOf(userId)).orElse(null);
        if (user == null)
            return null;
        user.setFullName(profileDTO.getFullName());
        user.setPhoneNumber(profileDTO.getPhoneNumber());
        user.setEmail(profileDTO.getEmail());
        user.setAddress(profileDTO.getAddress());
        rcUserDetailsRepository.save(user);
        UserProfileDTO updated = new UserProfileDTO();
        updated.setUserId(user.getUserId());
        updated.setFullName(user.getFullName());
        updated.setPhoneNumber(user.getPhoneNumber());
        updated.setEmail(user.getEmail());
        updated.setAddress(user.getAddress());
        return updated;
    }

    @Override
    public boolean updateExpoPushToken(String userId, String expoPushToken) {
        OnboardedUserEntity user = rcUserDetailsRepository.findById(Long.valueOf(userId)).orElse(null);
        if (user == null)
            return false;
        user.setExpoPushToken(expoPushToken);
        rcUserDetailsRepository.save(user);
        return true;
    }
}