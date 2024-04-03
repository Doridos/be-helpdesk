package cz.fel.cvut.behelpdesk.mapper;

import cz.fel.cvut.behelpdesk.dao.Employee;
import cz.fel.cvut.behelpdesk.dto.DetailEmployeeDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeDetailMapper {
    Employee toEntity(DetailEmployeeDto detailEmployeeDto);

    DetailEmployeeDto toDto(Employee employee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Employee partialUpdate(DetailEmployeeDto detailEmployeeDto, @MappingTarget Employee employee);
}