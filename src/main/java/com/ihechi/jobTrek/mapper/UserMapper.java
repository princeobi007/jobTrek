package com.ihechi.jobTrek.mapper;

import com.ihechi.jobTrek.domain.UserEntity;
import com.ihechi.jobTrek.dto.UserRegisterDto;
import com.ihechi.jobTrek.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(target = "id", ignore = true) // ID is auto-generated
  @Mapping(target = "password", ignore = true) // Password is hashed separately
  UserEntity toEntity(UserRegisterDto dto);

  UserResponseDto toDto(UserEntity entity);
}
