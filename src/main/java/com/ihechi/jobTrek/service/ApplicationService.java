package com.ihechi.jobTrek.service;

import com.ihechi.jobTrek.domain.ApplicationEntity;
import com.ihechi.jobTrek.domain.JobEntity;
import com.ihechi.jobTrek.domain.UserEntity;
import com.ihechi.jobTrek.dto.ApplicationCreateDto;
import com.ihechi.jobTrek.dto.ApplicationResponseDto;
import com.ihechi.jobTrek.exception.ResourceNotFoundException;
import com.ihechi.jobTrek.exception.UnauthorizedAccessException;
import com.ihechi.jobTrek.mapper.ApplicationMapper;
import com.ihechi.jobTrek.repository.ApplicationRepository;
import com.ihechi.jobTrek.repository.JobRepository;
import com.ihechi.jobTrek.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationService {
  private final ApplicationRepository applicationRepository;
  private final JobRepository jobRepository;
  private final UserRepository userRepository;

  @Transactional
  public ApplicationResponseDto applyForJob(ApplicationCreateDto request, Authentication authentication) {
    UserEntity user = getCurrentUser(authentication);

    if (!user.getRole().name().equals("DEVELOPER")) {
      throw new IllegalArgumentException("Only developers can apply for jobs");
    }

    JobEntity job = jobRepository.findById(request.jobId())
        .orElseThrow(() -> new IllegalArgumentException("Job not found"));

    ApplicationEntity application = ApplicationMapper.INSTANCE.toEntity(request);
    application.setJob(job);
    application.setApplicant(user);
    application = applicationRepository.save(application);

    return ApplicationMapper.INSTANCE.toDto(application);
  }

  public Page<ApplicationResponseDto> getJobApplications(Long jobId, Pageable pageable, Authentication authentication) {
    UserEntity user = getCurrentUser(authentication);

    JobEntity job = jobRepository.findById(jobId)
        .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

    if (!job.getPostedBy().equals(user)) {
      throw new UnauthorizedAccessException("Only the job creator can view applications");
    }

    List<ApplicationResponseDto> applicationDtos = job.getApplications().stream()
        .map(ApplicationMapper.INSTANCE::toDto)
        .collect(Collectors.toList());

    return new PageImpl<>(applicationDtos, pageable, applicationDtos.size());
  }

  public Page<ApplicationResponseDto> getUserApplications(Long userId, Pageable pageable, Authentication authentication) {
    return applicationRepository.findByApplicantId(userId, pageable)
        .map(ApplicationMapper.INSTANCE::toDto);
  }

  private UserEntity getCurrentUser(Authentication authentication){
    return userRepository.findByEmail(authentication.getName())
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
  }
}
