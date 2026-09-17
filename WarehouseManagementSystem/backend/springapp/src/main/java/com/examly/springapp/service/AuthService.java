
package com.examly.springapp.service;

import com.examly.springapp.dto.AuthResponse;
import com.examly.springapp.dto.LoginRequest;
import com.examly.springapp.dto.RegisterRequest;
import com.examly.springapp.entity.User;
import com.examly.springapp.enums.UserRole;
import com.examly.springapp.exception.*;
import com.examly.springapp.repository.UserRepository;
import com.examly.springapp.security.JwtUtil;
import com.examly.springapp.security.TokenBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    // Register new user
    public AuthResponse register(RegisterRequest request) {
        // Validate name (alphabets and spaces only)
        if (!request.getName().matches("^[a-zA-Z\\s]+$")) {
            throw new InvalidNameException("Name must contain only alphabets and spaces");
        }

        // Validate phone (exactly 10 digits)
        if (!request.getPhoneNumber().matches("^[0-9]{10}$")) {
            throw new InvalidPhoneException("Phone number must be exactly 10 digits");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("User", "email", request.getEmail());
        }

        // Determine role
        UserRole role = UserRole.GUEST;
        if (request.getRole() != null && !request.getRole().isEmpty()) {
            try {
                role = UserRole.valueOf(request.getRole().toUpperCase());
            } catch (IllegalArgumentException e) {
                role = UserRole.GUEST;
            }
        }

        // Create user entity
        User user = User.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .createdDate(LocalDateTime.now())
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);

        // Generate JWT token
        String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole().name());

        return AuthResponse.builder()
                .token(token)
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .role(savedUser.getRole().name())
                .userId(savedUser.getId())
                .message("Registration successful")
                .build();
    }

    // Login user
    public AuthResponse login(LoginRequest request) {
        // Authenticate credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Find user
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));

        // Update last login
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .userId(user.getId())
                .message("Login successful")
                .build();
    }

    // Logout user (blacklist token)
    public void logout(String token) {
        tokenBlacklistService.blacklistToken(token);
    }
}

