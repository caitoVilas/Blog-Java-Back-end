package com.caito.blogjava.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "users_messages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(length = 50)
    @Email
    private String email;
    @NotNull
    @Column(length = 200)
    private String message;
    @CreationTimestamp
    private LocalDateTime created;

}
