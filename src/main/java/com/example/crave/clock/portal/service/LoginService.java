package com.example.crave.clock.portal.service;

import com.example.crave.clock.portal.entity.OnboardedUserEntity;
import com.example.crave.clock.portal.request.LoginRequest;
import com.example.crave.clock.portal.request.UserRegistrationRequest;
import com.example.crave.clock.portal.response.LoginResponse;
import com.example.crave.clock.portal.dto.UserProfileDTO;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    public ResponseEntity<LoginResponse> onBoardUser(UserRegistrationRequest registrationRequest);

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest);
}
