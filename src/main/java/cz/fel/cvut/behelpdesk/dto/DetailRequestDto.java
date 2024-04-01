package cz.fel.cvut.behelpdesk.dto;

import cz.fel.cvut.behelpdesk.dao.Comment;
import cz.fel.cvut.behelpdesk.dao.Request;
import cz.fel.cvut.behelpdesk.enumeration.CategoryEnum;
import cz.fel.cvut.behelpdesk.enumeration.PriorityEnum;
import cz.fel.cvut.behelpdesk.enumeration.StateEnum;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link Request}
 */
public record DetailRequestDto(Long id, String name, String description, LocalDateTime dateOfAnnouncement,
                               LocalDateTime dateOfCompletion, String assignedByUsername, String assignedByForename,
                               String assignedBySurname, String assignedToUsername, String assignedToForename,
                               String assignedToSurname, List<CommentDto> comments, StateEnum requestState,
                               CategoryEnum requestCategory, PriorityEnum requestPriority) implements Serializable {
    /**
     * DTO for {@link Comment}
     */
    public record CommentDto(UUID id, String content, LocalDateTime dateOfComment, String authorUsername,
                             String authorForename, String authorSurname) implements Serializable {
    }
}