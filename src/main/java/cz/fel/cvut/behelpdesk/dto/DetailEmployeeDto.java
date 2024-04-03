package cz.fel.cvut.behelpdesk.dto;

import cz.fel.cvut.behelpdesk.dao.Employee;
import cz.fel.cvut.behelpdesk.enumeration.CategoryEnum;
import cz.fel.cvut.behelpdesk.enumeration.RoleEnum;

import java.io.Serializable;
import java.util.Collection;

/**
 * DTO for {@link Employee}
 */
public record DetailEmployeeDto(String username, String forename, String surname, RoleEnum userRole,
                                Collection<CategoryEnum> categories) implements Serializable {
}