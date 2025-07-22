package com.example.crave.clock.portal.serviceImpl;

import com.example.crave.clock.portal.entity.RcUserDetailsEntity;
import com.example.crave.clock.portal.filters.JwtAuthFilter;
import com.example.crave.clock.portal.repository.RcUserDetailsRepository;
import com.example.crave.clock.portal.request.LoginRequest;
import com.example.crave.clock.portal.response.LoginResponse;
import com.example.crave.clock.portal.service.LoginService;
import com.example.crave.clock.portal.util.JwtUtil;
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
    public ResponseEntity<LoginResponse> onBoardUser(LoginRequest loginRequest) {
        log.info("[LoginServiceImpl] onBoardUser called for email={}", loginRequest.getEmail());
        LoginResponse loginResponse = new LoginResponse();
        try {
            Optional<RcUserDetailsEntity> userExists = rcUserDetailsRepository.findByUsername(loginRequest.getEmail());

            if (userExists.isPresent()) {
                log.warn("[LoginServiceImpl] onBoardUser failed: username already taken for email={}",
                        loginRequest.getEmail());
                loginResponse.setStatus(false);
                loginResponse.setErrorMessage("Username already taken");
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(loginResponse);
            }
            RcUserDetailsEntity rcUserDetailsEntity = new RcUserDetailsEntity();
            String userId = rcUserDetailsRepository.getUserIdSeq();
            log.info("[LoginServiceImpl] onBoardUser new userId={}", userId);
            rcUserDetailsEntity.setUsername(loginRequest.getEmail());
            rcUserDetailsEntity.setPassword(jwtAuthFilter.passwordEncoder().encode(loginRequest.getPassword()));
            rcUserDetailsEntity.setUserId(userId);

            rcUserDetailsRepository.save(rcUserDetailsEntity);
            log.info("[LoginServiceImpl] onBoardUser user saved for email={}", loginRequest.getEmail());
        } catch (Exception e) {
            log.error("[LoginServiceImpl] onBoardUser error: {}", e.getMessage(), e);
            loginResponse.setStatus(false);
            loginResponse.setErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loginResponse);
        }
        loginResponse.setStatus(true);
        loginResponse.setErrorMessage("User Onboarded Successfully");
        log.info("[LoginServiceImpl] onBoardUser success for email={}", loginRequest.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        log.info("[LoginServiceImpl] login called for email={}", loginRequest.getEmail());
        LoginResponse loginResponse = new LoginResponse();
        String authToken;

        try {
            Optional<RcUserDetailsEntity> userExists = rcUserDetailsRepository.findByUsername(loginRequest.getEmail());
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
