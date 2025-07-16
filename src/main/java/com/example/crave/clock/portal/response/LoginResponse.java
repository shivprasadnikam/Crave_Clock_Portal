package com.example.crave.clock.portal.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String errorMessage;
    private boolean status;
    private String token;
    private String userId;
}
