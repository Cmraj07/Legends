package com.placement_portal.controller;

import com.placement_portal.payload.AuthRequestDTO;
import com.placement_portal.payload.JwtToken;
import com.placement_portal.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // User registration endpoint
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequestDTO authRequest) {
        // Register the user using AuthService
        authService.register(authRequest);
        return ResponseEntity.ok("User registered successfully");
    }

    // User login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequest) {
        try {
            JwtToken jwtToken = authService.login(authRequest.getUsername(), authRequest.getPassword());
            return ResponseEntity.ok(jwtToken);

        } catch (Exception e) {
            // Handle any unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login");
        }
    }

}
