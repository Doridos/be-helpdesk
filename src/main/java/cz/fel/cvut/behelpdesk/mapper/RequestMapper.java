package cz.fel.cvut.behelpdesk.mapper;

import cz.fel.cvut.behelpdesk.dao.Employee;
import cz.fel.cvut.behelpdesk.dao.Request;
import cz.fel.cvut.behelpdesk.dto.InputRequestDto;
import cz.fel.cvut.behelpdesk.dto.RequestDto;
import cz.fel.cvut.behelpdesk.service.EmployeeService;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EmployeeService.class})
public interface RequestMapper {
    List<Employee> map(List<String> value);

    @Mapping(target = "assignedBy", source = "assignedBy")
    Request toEntity(InputRequestDto requestDto);




    RequestDto toDto(Request request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Request partialUpdate(InputRequestDto requestDto, @MappingTarget Request request);
}