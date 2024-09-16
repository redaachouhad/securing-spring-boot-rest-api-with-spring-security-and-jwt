package com.example.restapispringsecurity.repository;

import com.example.restapispringsecurity.dto.RoleName;
import com.example.restapispringsecurity.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(RoleName roleName);
}
