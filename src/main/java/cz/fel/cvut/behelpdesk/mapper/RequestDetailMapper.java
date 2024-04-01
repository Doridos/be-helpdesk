package cz.fel.cvut.behelpdesk.mapper;

import cz.fel.cvut.behelpdesk.dao.Request;
import cz.fel.cvut.behelpdesk.dto.DetailRequestDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestDetailMapper {
    Request toEntity(DetailRequestDto detailRequestDto);

    @AfterMapping
    default void linkComments(@MappingTarget Request request) {
        request.getComments().forEach(comment -> comment.setRequest(request));
    }

    DetailRequestDto toDto(Request request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Request partialUpdate(DetailRequestDto detailRequestDto, @MappingTarget Request request);
}