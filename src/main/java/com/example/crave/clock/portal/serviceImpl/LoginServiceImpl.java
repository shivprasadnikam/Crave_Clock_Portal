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
    private  RcUserDetailsRepository rcUserDetailsRepository ;
    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public ResponseEntity<LoginResponse> onBoardUser(LoginRequest loginRequest)  {
        LoginResponse loginResponse = new LoginResponse();
        try {
            Optional<RcUserDetailsEntity> userExists = rcUserDetailsRepository.findByUsername(loginRequest.getEmail());

            if (userExists.isPresent()) {
                loginResponse.setStatus(false);
                loginResponse.setErrorMessage("Username already taken");
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(loginResponse);
            }
            RcUserDetailsEntity rcUserDetailsEntity = new RcUserDetailsEntity();
            String userId=rcUserDetailsRepository.getUserIdSeq();
            log.info("User Id :: {}",userId);
            rcUserDetailsEntity.setUsername(loginRequest.getEmail());
            rcUserDetailsEntity.setPassword(jwtAuthFilter.passwordEncoder().encode(loginRequest.getPassword()));
            rcUserDetailsEntity.setUserId(userId);

            rcUserDetailsRepository.save(rcUserDetailsEntity);
        } catch (Exception e) {
            loginResponse.setStatus(false);
            loginResponse.setErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loginResponse);
        }
        loginResponse.setStatus(true);
        loginResponse.setErrorMessage("User Onboarded Successfully");
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }
    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest)  {
        LoginResponse loginResponse = new LoginResponse();
        String authToken;

        try {
            System.out.println(" "+loginRequest.getEmail());
            Optional<RcUserDetailsEntity> userExists = rcUserDetailsRepository.findByUsername(loginRequest.getEmail());
            if(userExists.isEmpty()) {
                loginResponse.setStatus(false);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(loginResponse);
            }
            if (!jwtAuthFilter.passwordEncoder().matches(loginRequest.getPassword(), userExists.get().getPassword())) {
                loginResponse.setStatus(false);
                loginResponse.setErrorMessage("Invalid email or password");
                System.out.println("Invalid email or password");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(loginResponse);
            }
            authToken=jwtUtil.generateToken(loginRequest.getEmail());
        } catch (Exception e) {
            loginResponse.setStatus(false);
            loginResponse.setErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loginResponse);        }
        loginResponse.setStatus(true);
        loginResponse.setErrorMessage("User Logged In Successfully");
        loginResponse.setToken(authToken);
        loginResponse.setUserId(rcUserDetailsRepository.getUserIdByUserName(loginRequest.getEmail()));
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);

    }
}
