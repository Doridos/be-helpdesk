package cz.fel.cvut.behelpdesk.dto;

import cz.fel.cvut.behelpdesk.dao.Request;
import cz.fel.cvut.behelpdesk.enumeration.CategoryEnum;
import cz.fel.cvut.behelpdesk.enumeration.PriorityEnum;
import cz.fel.cvut.behelpdesk.enumeration.StateEnum;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link Request}
 */
public record InputRequestDto(String name, String description, LocalDateTime dateOfAnnouncement,
                              LocalDateTime dateOfCompletion, StateEnum requestState, CategoryEnum requestCategory,
                              PriorityEnum requestPriority, List<String> assignedTo, String assignedBy) implements Serializable {
}