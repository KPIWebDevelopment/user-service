package org.kpi.userservice.controller;

import org.kpi.userservice.dto.LoginRequest;
import org.kpi.userservice.dto.RegisterRequest;
import org.kpi.userservice.dto.ValidateTokenRequest;
import org.kpi.userservice.model.User;
import org.kpi.userservice.service.UserService;
import org.kpi.userservice.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String authenticateUser(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User userDetails = (User) authentication.getPrincipal();
        return jwtUtil.generateToken(userDetails.getEmail());
    }

    @PostMapping("/validate-request")
    public boolean validatePostRequest(@RequestBody ValidateTokenRequest request) {
        return jwtUtil.validateJwtToken(request.token());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = userService.register(request.name(), request.email(), request.password());
        return ResponseEntity.ok("User registered successfully: " + user.getEmail());
    }
}
