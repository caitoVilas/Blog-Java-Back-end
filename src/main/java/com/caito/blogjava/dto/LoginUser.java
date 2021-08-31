package com.caito.blogjava.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class LoginUser implements Serializable {

    private String userName;
    private String password;
}
