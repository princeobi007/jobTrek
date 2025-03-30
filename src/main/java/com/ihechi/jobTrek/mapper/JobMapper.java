package com.ihechi.jobTrek.mapper;

import com.ihechi.jobTrek.domain.JobEntity;
import com.ihechi.jobTrek.dto.JobCreateDto;
import com.ihechi.jobTrek.dto.JobResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobMapper {
  JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

  @Mapping(target = "id", ignore = true) // ID is auto-generated
  @Mapping(target = "postedAt", ignore = true) // Auto-set timestamp
  @Mapping(target = "postedBy", ignore = true) // Set manually in service
  JobEntity toEntity(JobCreateDto dto);

  JobResponseDto toDto(JobEntity entity);
}
