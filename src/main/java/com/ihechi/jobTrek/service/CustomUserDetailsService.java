package com.ihechi.jobTrek.service;

import com.ihechi.jobTrek.domain.UserEntity;
import com.ihechi.jobTrek.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;


  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

    return new User(user.getEmail(), user.getPassword(), user.getAuthorities());
  }
}
