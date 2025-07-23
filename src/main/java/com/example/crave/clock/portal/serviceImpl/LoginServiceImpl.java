package com.example.crave.clock.portal.serviceImpl;

import com.example.crave.clock.portal.entity.OnboardedUserEntity;
import com.example.crave.clock.portal.filters.JwtAuthFilter;
import com.example.crave.clock.portal.repository.RcUserDetailsRepository;
import com.example.crave.clock.portal.request.LoginRequest;
import com.example.crave.clock.portal.request.UserRegistrationRequest;
import com.example.crave.clock.portal.response.LoginResponse;
import com.example.crave.clock.portal.service.LoginService;
import com.example.crave.clock.portal.util.JwtUtil;
import com.example.crave.clock.portal.dto.UserProfileDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private RcUserDetailsRepository rcUserDetailsRepository;
    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<LoginResponse> onBoardUser(UserRegistrationRequest registrationRequest) {
        log.info("[LoginServiceImpl] onBoardUser called for email={}", registrationRequest.getEmail());
        LoginResponse loginResponse = new LoginResponse();
        try {
            Optional<OnboardedUserEntity> userExists = rcUserDetailsRepository
                    .findByUsername(registrationRequest.getEmail());

            if (userExists.isPresent()) {
                log.warn("[LoginServiceImpl] onBoardUser failed: username already taken for email={}",
                        registrationRequest.getEmail());
                loginResponse.setStatus(false);
                loginResponse.setErrorMessage("Username already taken");
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(loginResponse);
            }
            OnboardedUserEntity onboardedUserEntity = new OnboardedUserEntity();
            String userId = rcUserDetailsRepository.getUserIdSeq();
            log.info("[LoginServiceImpl] onBoardUser new userId={}", userId);
            onboardedUserEntity.setUsername(registrationRequest.getEmail());
            onboardedUserEntity.setPassword(jwtAuthFilter.passwordEncoder().encode(registrationRequest.getPassword()));
            onboardedUserEntity.setUserId(userId);
            onboardedUserEntity.setFullName(registrationRequest.getFullName());
            onboardedUserEntity.setPhoneNumber(registrationRequest.getPhoneNumber());
            onboardedUserEntity.setEmail(registrationRequest.getEmail());
            onboardedUserEntity.setAddress(registrationRequest.getAddress());

            rcUserDetailsRepository.save(onboardedUserEntity);
            log.info("[LoginServiceImpl] onBoardUser user saved for email={}", registrationRequest.getEmail());
        } catch (Exception e) {
            log.error("[LoginServiceImpl] onBoardUser error: {}", e.getMessage(), e);
            loginResponse.setStatus(false);
            loginResponse.setErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loginResponse);
        }
        loginResponse.setStatus(true);
        loginResponse.setErrorMessage("User Onboarded Successfully");
        log.info("[LoginServiceImpl] onBoardUser success for email={}", registrationRequest.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        log.info("[LoginServiceImpl] login called for email={}", loginRequest.getEmail());
        LoginResponse loginResponse = new LoginResponse();
        String authToken;

        try {
            Optional<OnboardedUserEntity> userExists = rcUserDetailsRepository.findByUsername(loginRequest.getEmail());
            if (userExists.isEmpty()) {
                log.warn("[LoginServiceImpl] login failed: user not found for email={}", loginRequest.getEmail());
                loginResponse.setStatus(false);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(loginResponse);
            }
            if (!jwtAuthFilter.passwordEncoder().matches(loginRequest.getPassword(), userExists.get().getPassword())) {
                log.warn("[LoginServiceImpl] login failed: invalid password for email={}", loginRequest.getEmail());
                loginResponse.setStatus(false);
                loginResponse.setErrorMessage("Invalid email or password");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(loginResponse);
            }
            authToken = jwtUtil.generateToken(loginRequest.getEmail());
            log.info("[LoginServiceImpl] login success: token generated for email={}", loginRequest.getEmail());
        } catch (Exception e) {
            log.error("[LoginServiceImpl] login error: {}", e.getMessage(), e);
            loginResponse.setStatus(false);
            loginResponse.setErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loginResponse);
        }
        loginResponse.setStatus(true);
        loginResponse.setErrorMessage("User Logged In Successfully");
        loginResponse.setToken(authToken);
        loginResponse.setUserId(rcUserDetailsRepository.getUserIdByUserName(loginRequest.getEmail()));
        log.info("[LoginServiceImpl] login success for email={}", loginRequest.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);

    }
}
