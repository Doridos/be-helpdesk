package cz.fel.cvut.behelpdesk.dao;

import cz.fel.cvut.behelpdesk.enumeration.CategoryEnum;
import cz.fel.cvut.behelpdesk.enumeration.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
public class Employee {
    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(nullable = false)
    private String forename;

    @Column(nullable = false)
    private String surname;

    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

    @OneToMany(mappedBy = "assignedBy")
    private List<Request> assignedByMe;

    @OneToMany(mappedBy = "assignedTo")
    private List<Request> assignedToMe;

    @Enumerated(EnumType.STRING)
    private RoleEnum userRole;

    @ElementCollection(fetch = FetchType.EAGER)
    Collection<CategoryEnum> categories;



}
