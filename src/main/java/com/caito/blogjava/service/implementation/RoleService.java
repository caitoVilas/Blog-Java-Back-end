package com.caito.blogjava.service.implementation;

import com.caito.blogjava.entity.Role;
import com.caito.blogjava.enums.RoleName;
import com.caito.blogjava.repository.RoleRepository;
import com.caito.blogjava.service.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRolService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> getRole(RoleName roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
