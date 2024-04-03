package cz.fel.cvut.behelpdesk.mapper;

import cz.fel.cvut.behelpdesk.dao.Employee;
import cz.fel.cvut.behelpdesk.dto.InputEmployeeCategoriesDto;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {
    Employee toEntity(InputEmployeeCategoriesDto inputEmployeeCategoriesDto);

    InputEmployeeCategoriesDto toDto(Employee employee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Employee partialUpdate(InputEmployeeCategoriesDto inputEmployeeCategoriesDto, @MappingTarget Employee employee);
}