package com.caito.blogjava.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtDto {
    private String jwt;
    private String userName;
    private Collection<? extends GrantedAuthority> authorities;
}
