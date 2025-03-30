package com.ihechi.jobTrek.security;

import com.ihechi.jobTrek.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain) throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      chain.doFilter(request, response);
      return;
    }

    final String token = authHeader.substring(7);
    String userEmail = null;

    try {
      userEmail = jwtUtil.extractUsername(token);
    } catch (ExpiredJwtException e) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
      return;
    } catch (Exception e) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
      return;
    }

    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      var userEntity = userRepository.findByEmail(userEmail)
          .orElse(null);

      if (userEntity != null && jwtUtil.validateToken(token, userEntity.getEmail())) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            new User(userEntity.getEmail(), userEntity.getPassword(), userEntity.getAuthorities()),
            null,
            Collections.emptyList()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    chain.doFilter(request, response);
  }

}
