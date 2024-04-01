package cz.fel.cvut.behelpdesk.dto;

import cz.fel.cvut.behelpdesk.dao.Request;
import cz.fel.cvut.behelpdesk.enumeration.CategoryEnum;
import cz.fel.cvut.behelpdesk.enumeration.PriorityEnum;
import cz.fel.cvut.behelpdesk.enumeration.StateEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Request}
 */
public record RequestDto(Long id, String name, String description, LocalDateTime dateOfAnnouncement,
                         LocalDateTime dateOfCompletion, StateEnum requestState, CategoryEnum requestCategory,
                         PriorityEnum requestPriority) implements Serializable {
}