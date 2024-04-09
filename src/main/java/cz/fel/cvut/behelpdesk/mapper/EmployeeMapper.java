package cz.fel.cvut.behelpdesk.mapper;

import cz.fel.cvut.behelpdesk.dao.Employee;
import cz.fel.cvut.behelpdesk.dto.EmployeeUpdateDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {
    Employee toEntity(EmployeeUpdateDto employeeUpdateDto);

    EmployeeUpdateDto toDto(Employee employee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Employee partialUpdate(EmployeeUpdateDto employeeUpdateDto, @MappingTarget Employee employee);
}