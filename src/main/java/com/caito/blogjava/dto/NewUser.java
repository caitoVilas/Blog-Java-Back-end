package com.caito.blogjava.dto;

import com.caito.blogjava.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewUser implements Serializable {
    private String name;
    private String userName;
    private String email;
    private String password;
    private String roles;
}
