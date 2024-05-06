package cz.fel.cvut.behelpdesk.dao;

import cz.fel.cvut.behelpdesk.enumeration.CategoryEnum;
import cz.fel.cvut.behelpdesk.enumeration.PriorityEnum;
import cz.fel.cvut.behelpdesk.enumeration.StateEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime dateOfAnnouncement;

    @Column
    private LocalDateTime dateOfCompletion;

    @ManyToOne
    @JoinColumn(referencedColumnName = "username")
    private Employee assignedBy;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "username"))
    private List<Employee> assignedTo;

    @OneToMany(mappedBy = "request")
    private List<Comment> comments;


    @Enumerated(EnumType.STRING)
    private StateEnum requestState;

    @Enumerated(EnumType.STRING)
    private CategoryEnum requestCategory;

    @Enumerated(EnumType.STRING)
    private PriorityEnum requestPriority;

}
