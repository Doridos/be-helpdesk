package cz.fel.cvut.behelpdesk.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link cz.fel.cvut.behelpdesk.dao.Comment}
 */
public record CommentDto(UUID id, String content, LocalDateTime dateOfComment,
                         EmployeeDto author) implements Serializable {
}