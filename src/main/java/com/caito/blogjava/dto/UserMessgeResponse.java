package com.caito.blogjava.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class UserMessgeResponse {

    private Long id;
    private String email;
    private String message;
    private LocalDateTime created;
}
