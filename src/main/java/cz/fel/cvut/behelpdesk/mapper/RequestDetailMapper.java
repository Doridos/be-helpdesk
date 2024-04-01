package cz.fel.cvut.behelpdesk.mapper;

import cz.fel.cvut.behelpdesk.dao.Request;
import cz.fel.cvut.behelpdesk.dto.DetailRequestDto;
import cz.fel.cvut.behelpdesk.service.EmployeeService;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EmployeeService.class})
public interface RequestDetailMapper {

    @Mapping(target = "assignedBy", source = "assignedByUsername")
    @Mapping(target = "assignedTo", source = "assignedToUsername")
    Request toEntity(DetailRequestDto detailRequestDto);

    @AfterMapping
    default void linkComments(@MappingTarget Request request) {
        request.getComments().forEach(comment -> comment.setRequest(request));
    }

    DetailRequestDto toDto(Request request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Request partialUpdate(DetailRequestDto detailRequestDto, @MappingTarget Request request);
}