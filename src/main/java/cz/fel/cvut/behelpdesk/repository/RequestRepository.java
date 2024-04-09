package cz.fel.cvut.behelpdesk.repository;

import cz.fel.cvut.behelpdesk.dao.Comment;
import cz.fel.cvut.behelpdesk.dao.Employee;
import cz.fel.cvut.behelpdesk.dao.Request;
import cz.fel.cvut.behelpdesk.enumeration.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByAssignedTo(List<Employee> employees);
    List<Request> findByRequestCategory(CategoryEnum category);
    List<Request> findByAssignedBy(Employee employee);
}