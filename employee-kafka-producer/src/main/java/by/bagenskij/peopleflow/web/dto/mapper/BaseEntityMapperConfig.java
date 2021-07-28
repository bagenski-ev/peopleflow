package by.bagenskij.peopleflow.web.dto.mapper;

import by.bagenskij.peopleflow.dao.model.BaseEntity;
import by.bagenskij.peopleflow.web.dto.model.BaseEntityDto;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;

@MapperConfig(mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG)
public interface BaseEntityMapperConfig {
  // Not intended to be generated, but to carry inheritable mapping annotations:
  @Mapping(target = "id", source = "id")
  BaseEntity anyDtoToEntity(BaseEntityDto dto);

  @Mapping(target = "id", source = "id")
  BaseEntityDto anyEntityToBaseEntityDto(BaseEntity entity);
}
