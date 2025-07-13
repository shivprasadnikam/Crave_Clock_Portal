package com.example.crave.clock.portal.controller;

import com.example.crave.clock.portal.request.LoginRequest;
import com.example.crave.clock.portal.response.LoginResponse;
import com.example.crave.clock.portal.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LoginScreenController {
    @Autowired
    private  LoginService loginService;
    @PostMapping("/onBoardUser")
    public ResponseEntity<LoginResponse> onBoarUserController(@RequestBody LoginRequest loginRequest){
        long startTime = System.currentTimeMillis();
        return loginService.onBoardUser(loginRequest);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginController(@RequestBody LoginRequest loginRequest){
        long startTime = System.currentTimeMillis();
        System.out.printf("Login called");
        return loginService.login(loginRequest);
    }
}
