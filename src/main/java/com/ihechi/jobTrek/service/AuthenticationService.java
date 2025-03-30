package com.ihechi.jobTrek.service;

import com.ihechi.jobTrek.domain.UserEntity;
import com.ihechi.jobTrek.dto.AuthResponseDto;
import com.ihechi.jobTrek.dto.UserLoginDto;
import com.ihechi.jobTrek.dto.UserRegisterDto;
import com.ihechi.jobTrek.mapper.UserMapper;
import com.ihechi.jobTrek.repository.UserRepository;
import com.ihechi.jobTrek.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;

  /**
   * Handles user registration.
   */
  public AuthResponseDto register(UserRegisterDto request) {
    if (userRepository.findByEmail(request.email()).isPresent()) {
      throw new IllegalArgumentException("Email is already taken");
    }

    UserEntity user = new UserEntity();
    user.setEmail(request.email());
    user.setPassword(passwordEncoder.encode(request.password())); // Hash password
    user.setRole(request.role());

    userRepository.save(user);

    String token = jwtUtil.generateToken(user.getEmail());
    return new AuthResponseDto(token, UserMapper.INSTANCE.toDto(user));
  }

  /**
   * Handles user login and JWT token generation.
   */
  public AuthResponseDto login(UserLoginDto request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.email(), request.password())
    );

    UserEntity user = userRepository.findByEmail(request.email())
        .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

    String token = jwtUtil.generateToken(user.getEmail());
    return new AuthResponseDto(token, UserMapper.INSTANCE.toDto(user));
  }
}
