package cz.fel.cvut.behelpdesk.dto;

import cz.fel.cvut.behelpdesk.dao.Comment;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Comment}
 */
public record InputCommentDto(String content, LocalDateTime dateOfComment, String authorUsername,
                              Long requestId) implements Serializable {
}