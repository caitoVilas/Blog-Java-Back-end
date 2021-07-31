package com.caito.blogjava.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class NewUserMessage implements Serializable {
    private Long id;
    private String email;
    private String message;
}
