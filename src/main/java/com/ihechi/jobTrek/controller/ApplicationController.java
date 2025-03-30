package com.ihechi.jobTrek.controller;

import com.ihechi.jobTrek.dto.ApplicationCreateDto;
import com.ihechi.jobTrek.dto.ApplicationResponseDto;
import com.ihechi.jobTrek.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApplicationController extends BaseV1Controller {

  private final ApplicationService applicationService;

  @PostMapping("/applications")
  public ResponseEntity<ApplicationResponseDto> applyForJob(
      @Valid @RequestBody ApplicationCreateDto request, Authentication authentication) {
    return ResponseEntity.ok(applicationService.applyForJob(request, authentication));
  }

  @GetMapping("/applications/job/{jobId}")
  public ResponseEntity<Page<ApplicationResponseDto>> getJobApplications(@PathVariable Long jobId,
      Pageable pageable, Authentication authentication) {
    return ResponseEntity.ok(
        applicationService.getJobApplications(jobId, pageable, authentication));
  }

  @GetMapping("/applications/user/{userId}")
  public ResponseEntity<Page<ApplicationResponseDto>> getUserApplications(@PathVariable Long userId,
      Pageable pageable, Authentication authentication) {
    return ResponseEntity.ok(
        applicationService.getUserApplications(userId, pageable, authentication));
  }
}
