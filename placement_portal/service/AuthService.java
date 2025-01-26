package com.placement_portal.service;

import com.placement_portal.model.User;
import com.placement_portal.payload.AuthRequestDTO;
import com.placement_portal.payload.JwtToken;
import com.placement_portal.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(AuthRequestDTO authRequest) {
        // Creating a new user
        User user = new User();
        user.setUsername(authRequest.getUsername());
        // Encoding the password
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        // Setting the default role if not provided
        user.setRole(authRequest.getRole() != null ? authRequest.getRole() : "ROLE_USER");
        // Save user to the repository
        return userRepository.save(user);
    }

    public JwtToken login(String username, String password) {

            // Generate JWT token
            String token = jwtService.generateToken(username);

            // Wrap the token in JwtToken object
            JwtToken jwtToken = new JwtToken();
            jwtToken.setToken(token);
            jwtToken.setType("JWT");

            return jwtToken;

    }

}
