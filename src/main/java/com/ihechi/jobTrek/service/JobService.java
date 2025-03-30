package com.ihechi.jobTrek.service;

import com.ihechi.jobTrek.domain.JobEntity;
import com.ihechi.jobTrek.domain.UserEntity;
import com.ihechi.jobTrek.dto.JobCreateDto;
import com.ihechi.jobTrek.dto.JobResponseDto;
import com.ihechi.jobTrek.mapper.JobMapper;
import com.ihechi.jobTrek.repository.JobRepository;
import com.ihechi.jobTrek.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobService {
  private final JobRepository jobRepository;
  private final UserRepository userRepository;

  @Transactional
  public JobResponseDto createJob(JobCreateDto request, Authentication authentication) {
    UserEntity user = userRepository.findByEmail(authentication.getName())
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    if (!user.getRole().name().equals("COMPANY")) {
      throw new IllegalArgumentException("Only companies can post jobs");
    }

    JobEntity job = JobMapper.INSTANCE.toEntity(request);
    job.setPostedBy(user);
    job = jobRepository.save(job);

    return JobMapper.INSTANCE.toDto(job);
  }

  public Page<JobResponseDto> getAllJobs(Pageable pageable) {
    return jobRepository.findAll(pageable).map(JobMapper.INSTANCE::toDto);
  }

  public JobResponseDto getJobById(Long jobId) {
    JobEntity job = jobRepository.findById(jobId)
        .orElseThrow(() -> new IllegalArgumentException("Job not found"));
    return JobMapper.INSTANCE.toDto(job);
  }

  @Transactional
  public void deleteJob(Long jobId, Authentication authentication) {
    UserEntity user = userRepository.findByEmail(authentication.getName())
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    JobEntity job = jobRepository.findById(jobId)
        .orElseThrow(() -> new IllegalArgumentException("Job not found"));

    if (!job.getPostedBy().equals(user)) {
      throw new IllegalArgumentException("Only the posting company can delete this job");
    }

    jobRepository.delete(job);
  }
}
