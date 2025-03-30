package com.ihechi.jobTrek.mapper;

import com.ihechi.jobTrek.domain.ApplicationEntity;
import com.ihechi.jobTrek.dto.ApplicationCreateDto;
import com.ihechi.jobTrek.dto.ApplicationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ApplicationMapper {
  ApplicationMapper INSTANCE = Mappers.getMapper(ApplicationMapper.class);

  @Mapping(target = "id", ignore = true) // ID is auto-generated
  @Mapping(target = "appliedAt", ignore = true) // Auto-set timestamp
  @Mapping(target = "job", ignore = true) // Set manually in service
  @Mapping(target = "applicant", ignore = true) // Set manually in service
  ApplicationEntity toEntity(ApplicationCreateDto dto);

  @Mapping(target = "jobId", source = "job.id")
  @Mapping(target = "applicantId", source = "applicant.id")
  ApplicationResponseDto toDto(ApplicationEntity entity);
}
