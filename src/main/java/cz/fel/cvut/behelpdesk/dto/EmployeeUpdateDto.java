package cz.fel.cvut.behelpdesk.dto;

import cz.fel.cvut.behelpdesk.enumeration.CategoryEnum;
import cz.fel.cvut.behelpdesk.enumeration.RoleEnum;

import java.io.Serializable;
import java.util.Collection;

/**
 * DTO for {@link cz.fel.cvut.behelpdesk.dao.Employee}
 */
public record EmployeeUpdateDto(RoleEnum userRole, Collection<CategoryEnum> categories) implements Serializable {
}