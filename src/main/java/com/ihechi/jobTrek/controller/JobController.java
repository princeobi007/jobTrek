package com.ihechi.jobTrek.controller;

import com.ihechi.jobTrek.dto.JobCreateDto;
import com.ihechi.jobTrek.dto.JobResponseDto;
import com.ihechi.jobTrek.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JobController extends BaseV1Controller{
  private final JobService jobService;

  @PostMapping("/jobs")
  public ResponseEntity<JobResponseDto> createJob(@Valid @RequestBody JobCreateDto request, Authentication authentication) {
    return ResponseEntity.ok(jobService.createJob(request, authentication));
  }

  @GetMapping("/jobs")
  public ResponseEntity<Page<JobResponseDto>> getAllJobs(Pageable pageable) {
    return ResponseEntity.ok(jobService.getAllJobs(pageable));
  }

  @GetMapping("/jobs/{id}")
  public ResponseEntity<JobResponseDto> getJobById(@PathVariable Long id) {
    return ResponseEntity.ok(jobService.getJobById(id));
  }

  @DeleteMapping("/jobs/{id}")
  public ResponseEntity<Void> deleteJob(@PathVariable Long id, Authentication authentication) {
    jobService.deleteJob(id, authentication);
    return ResponseEntity.noContent().build();
  }
}
