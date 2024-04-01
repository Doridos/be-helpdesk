package cz.fel.cvut.behelpdesk.dto;

import cz.fel.cvut.behelpdesk.dao.Employee;

import java.io.Serializable;

/**
 * DTO for {@link Employee}
 */
public record ShortEmployeeDto(String username, String forename, String surname) implements Serializable {
}