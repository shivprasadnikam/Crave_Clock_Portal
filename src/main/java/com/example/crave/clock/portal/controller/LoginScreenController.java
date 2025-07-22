package com.example.crave.clock.portal.controller;

import com.example.crave.clock.portal.request.LoginRequest;
import com.example.crave.clock.portal.response.LoginResponse;
import com.example.crave.clock.portal.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class LoginScreenController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/onBoardUser")
    public ResponseEntity<LoginResponse> onBoarUserController(@RequestBody LoginRequest loginRequest) {
        log.info("[LoginScreenController] onBoardUser called for email={}", loginRequest.getEmail());
        ResponseEntity<LoginResponse> response = loginService.onBoardUser(loginRequest);
        log.info("[LoginScreenController] onBoardUser result: status={}, message={}",
                response.getBody() != null ? response.getBody().isStatus() : null,
                response.getBody() != null ? response.getBody().getErrorMessage() : null);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginController(@RequestBody LoginRequest loginRequest) {
        log.info("[LoginScreenController] login called for email={}", loginRequest.getEmail());
        ResponseEntity<LoginResponse> response = loginService.login(loginRequest);
        log.info("[LoginScreenController] login result: status={}, message={}",
                response.getBody() != null ? response.getBody().isStatus() : null,
                response.getBody() != null ? response.getBody().getErrorMessage() : null);
        return response;
    }
}
