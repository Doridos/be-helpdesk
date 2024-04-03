package cz.fel.cvut.behelpdesk.dto;

import cz.fel.cvut.behelpdesk.dao.Employee;
import cz.fel.cvut.behelpdesk.enumeration.CategoryEnum;

import java.io.Serializable;
import java.util.Collection;

/**
 * DTO for {@link Employee}
 */
public record InputEmployeeCategoriesDto(Collection<CategoryEnum> categories) implements Serializable {
}