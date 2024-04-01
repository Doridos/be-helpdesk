package cz.fel.cvut.behelpdesk.mapper;

import cz.fel.cvut.behelpdesk.dao.Request;
import cz.fel.cvut.behelpdesk.dto.InputRequestDto;
import cz.fel.cvut.behelpdesk.dto.RequestDto;
import cz.fel.cvut.behelpdesk.service.EmployeeService;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EmployeeService.class})
public interface RequestMapper {
    @Mapping(target = "assignedTo", source = "assignedTo")
    @Mapping(target = "assignedBy", source = "assignedBy")
    Request toEntity(InputRequestDto requestDto);

    RequestDto toDto(Request request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Request partialUpdate(InputRequestDto requestDto, @MappingTarget Request request);
}