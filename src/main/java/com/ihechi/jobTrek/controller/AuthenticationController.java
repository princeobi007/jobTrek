package com.ihechi.jobTrek.controller;

import com.ihechi.jobTrek.dto.AuthResponseDto;
import com.ihechi.jobTrek.dto.UserLoginDto;
import com.ihechi.jobTrek.dto.UserRegisterDto;
import com.ihechi.jobTrek.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  /**
   * Register a new user.
   */
  @PostMapping("/register")
  public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody UserRegisterDto request) {
    return ResponseEntity.ok(authenticationService.register(request));
  }

  /**
   * Login and get JWT token.
   */
  @PostMapping("/login")
  public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody UserLoginDto request) {
    return ResponseEntity.ok(authenticationService.login(request));
  }
}
