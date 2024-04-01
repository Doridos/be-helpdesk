package cz.fel.cvut.behelpdesk.repository;

import cz.fel.cvut.behelpdesk.dao.Comment;
import cz.fel.cvut.behelpdesk.dao.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {

}