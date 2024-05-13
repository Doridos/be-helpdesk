package cz.fel.cvut.behelpdesk.repository;

import cz.fel.cvut.behelpdesk.dao.Employee;
import cz.fel.cvut.behelpdesk.dao.Request;
import cz.fel.cvut.behelpdesk.enumeration.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByAssignedTo(List<Employee> employees);
    List<Request> findByRequestCategory(CategoryEnum category);
    List<Request> findByRequestCategoryIn(List<CategoryEnum> categories);
    List<Request> findByAssignedBy(Employee employee);
    @Query("SELECT r FROM Request r WHERE r.dateOfAnnouncement >= :startDate")
    List<Request> findAllOpenedFrom(@Param("startDate") LocalDateTime startDate);
}