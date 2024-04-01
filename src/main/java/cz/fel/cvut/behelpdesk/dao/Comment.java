package cz.fel.cvut.behelpdesk.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column
    private String content;

    @Column
    private LocalDateTime dateOfComment;

    @ManyToOne
    @JoinColumn(referencedColumnName = "username")
    private Employee author;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Request request;
}
