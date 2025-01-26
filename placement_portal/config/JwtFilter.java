package com.placement_portal.config;

import com.placement_portal.model.User;
import com.placement_portal.repository.UserRepository;
import com.placement_portal.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String token = request.getHeader("Authorization");
//        if (token != null && token.startsWith("Bearer ")) {   // if token not null then decrpyt token
//            String jwtToken = token.substring(8, token.length() - 1);
//            String username = jwtService.getUsername(jwtToken);
//            Optional<User> opUsername = userRepository.findByUsername(username);  // optional avoid null point exception
//
//            if (opUsername.isPresent()) {
//                User user = opUsername.get();
//                UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(new SimpleGrantedAuthority(user.getRole())));
//                userToken.setDetails(new WebAuthenticationDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(userToken);
//            }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        // Check for Authorization header and JWT format
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(8, token.length() - 1);
            try {
                // Extract username from token
                String username = jwtService.getUsername(jwtToken);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Fetch user details from database
                    Optional<User> optionalUser = userRepository.findByUsername(username);
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();

                        // Create authentication token with user's role
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
                        );

                        // Set authentication details
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        // Update SecurityContext
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception e) {
                // Log the exception and continue the filter chain
                System.err.println("Error validating JWT: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Optionally send a 401 status
            }
        }
        // Proceed with the next filter in the chain
        filterChain.doFilter(request, response);
    }
}
