package com.example.crave.clock.portal.controller;

import com.example.crave.clock.portal.dto.UserProfileDTO;
import com.example.crave.clock.portal.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/profile")
@Slf4j
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileDTO> getUserProfileById(@PathVariable String userId) {
        log.info("[API REQUEST] GET /api/profile/{} - Fetching user profile", userId);
        UserProfileDTO profile = profileService.getUserProfileById(userId);
        if (profile == null) {
            log.warn("[API RESPONSE] GET /api/profile/{} - User not found", userId);
            return ResponseEntity.notFound().build();
        }
        log.info("[API RESPONSE] GET /api/profile/{} - User profile fetched successfully", userId);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserProfileDTO> updateUserProfile(@PathVariable String userId,
            @RequestBody UserProfileDTO profileDTO) {
        log.info("[API REQUEST] PUT /api/profile/{} - Updating user profile", userId);
        UserProfileDTO updated = profileService.updateUserProfile(userId, profileDTO);
        if (updated == null) {
            log.warn("[API RESPONSE] PUT /api/profile/{} - User not found", userId);
            return ResponseEntity.notFound().build();
        }
        log.info("[API RESPONSE] PUT /api/profile/{} - User profile updated successfully", userId);
        return ResponseEntity.ok(updated);
    }
}