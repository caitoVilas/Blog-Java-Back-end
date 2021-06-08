package com.caito.blogjava.dto;

import com.caito.blogjava.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse implements Serializable {
    private Long id;
    private String name;
    private String userName;
    private String email;
    private Set<Role> roles = new HashSet<>();
}
