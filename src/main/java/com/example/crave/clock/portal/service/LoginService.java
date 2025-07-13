package com.example.crave.clock.portal.service;

import com.example.crave.clock.portal.request.LoginRequest;
import com.example.crave.clock.portal.response.LoginResponse;
import org.springframework.http.ResponseEntity;


public interface LoginService  {
    public ResponseEntity<LoginResponse> onBoardUser(LoginRequest loginRequest) ;
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) ;
}
