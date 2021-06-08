package com.caito.blogjava.service;

import com.caito.blogjava.entity.Role;
import com.caito.blogjava.enums.RoleName;

import java.util.Optional;

public interface IRolService {
    public Role saveRole(Role role);
    public Optional<Role> getRole(RoleName roleName);
}
