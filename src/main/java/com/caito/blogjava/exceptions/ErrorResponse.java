package com.caito.blogjava.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public final class ErrorResponse {
    private Integer status;
    private LocalDateTime timestamp;
    private String message;
    private String path;
}
