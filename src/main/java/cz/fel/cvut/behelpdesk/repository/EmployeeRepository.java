package cz.fel.cvut.behelpdesk.repository;

import cz.fel.cvut.behelpdesk.dao.Employee;
import cz.fel.cvut.behelpdesk.enumeration.CategoryEnum;
import cz.fel.cvut.behelpdesk.enumeration.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    List<Employee> findByUserRole(RoleEnum userRole);
    List<Employee> findByCategories(CategoryEnum categories);
    long countByUsername(String username);
}