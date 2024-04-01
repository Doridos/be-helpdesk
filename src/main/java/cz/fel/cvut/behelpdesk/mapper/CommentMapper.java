package cz.fel.cvut.behelpdesk.mapper;

import cz.fel.cvut.behelpdesk.dao.Comment;
import cz.fel.cvut.behelpdesk.dto.CommentDto;
import cz.fel.cvut.behelpdesk.dto.InputCommentDto;
import cz.fel.cvut.behelpdesk.service.EmployeeService;
import cz.fel.cvut.behelpdesk.service.RequestService;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EmployeeService.class, RequestService.class})
public interface CommentMapper {
    @Mapping(target = "request", source = "requestId")
    @Mapping(target = "author", source = "authorUsername")
    Comment toEntity(InputCommentDto commentDto);

    CommentDto toDto(Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment partialUpdate(CommentDto commentDto, @MappingTarget Comment comment);
}