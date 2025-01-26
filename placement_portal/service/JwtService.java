package com.placement_portal.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private int expiry; // Expiry duration in milliseconds

    private Algorithm algorithm;

    @PostConstruct
    public void initAlgorithm() {
        // Initialize the HMAC algorithm with the secret key
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    /**
     * Generates a JWT token for the given username.
     *
     * @param username the username to include in the token
     * @return the generated JWT token
     */
    public String generateToken(String username) {
        return JWT.create()
                .withClaim("name", username) // Add custom claim "name"
                .withExpiresAt(new Date(System.currentTimeMillis() + expiry)) // Expiry time
                .withIssuer(issuer) // Issuer information
                .sign(algorithm); // Sign the token with the HMAC algorithm
    }


    /**
     * Extracts the username from the given JWT token after validation.
     *
     * @param token the JWT token to validate and decode
     * @return the username contained in the token
     * @throws IllegalArgumentException if the token is invalid or expired
     */
    public String getUsername(String token) {
        try {
            // Validate and decode the JWT token
            System.out.println("Received Token: " + token);  // Debug log
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer(issuer) // Ensure token was issued by this service
                    .build()
                    .verify(token);
            return decodedJWT.getClaim("name").asString();
        } catch (Exception e) {
            System.err.println("Error decoding JWT token: " + e.getMessage());
            throw new IllegalArgumentException("Invalid JWT token", e);
        }
    }
}