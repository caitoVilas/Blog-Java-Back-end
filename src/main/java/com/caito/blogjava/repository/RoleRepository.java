package com.caito.blogjava.repository;

import com.caito.blogjava.entity.Role;
import com.caito.blogjava.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Optional<Role> findByRoleName(RoleName roleName);
}
