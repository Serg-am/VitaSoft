package org.example.vitasoft.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserWebApp user;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Column(columnDefinition = "TEXT")
    private String text;
}

