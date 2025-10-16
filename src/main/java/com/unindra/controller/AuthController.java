package com.unindra.controller;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unindra.model.request.LoginRequest;
import com.unindra.model.response.TokenResponse;
import com.unindra.model.response.WebResponse;
import com.unindra.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<WebResponse<TokenResponse>> login(@RequestBody LoginRequest request) {
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .findFirst().map(GrantedAuthority::getAuthority).orElse("ROLE_UNKNOWN");

        TokenResponse token = jwtUtil.generateToken(userDetails.getUsername(), role);

        return ResponseEntity.ok(
                WebResponse.<TokenResponse>builder()
                        .data(token)
                        .message("Berhasil masuk")
                        .build());
    }
}